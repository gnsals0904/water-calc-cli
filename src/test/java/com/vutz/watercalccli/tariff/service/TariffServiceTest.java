package com.vutz.watercalccli.tariff.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vutz.watercalccli.tariff.dto.PriceResponse;
import com.vutz.watercalccli.tariff.dto.Tariff;
import com.vutz.watercalccli.tariff.repository.TariffRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("TariffService 단위 테스트")
class TariffServiceTest {

    @Spy
    private TariffRepository tariffRepository;

    @InjectMocks
    private TariffService tariffService;

    private final String cityName = "서울시";
    private final String sectorName = "가정용";
    private final Tariff tariff1 = new Tariff(1, "서울시", "가정용", 10, 50, 1000);
    private final Tariff tariff2 = new Tariff(2, "서울시", "가정용", 50, 100, 2000);
    private final Tariff tariff3 = new Tariff(3, "서울시", "가정용", 100, 150, 3000);

    @Test
    @DisplayName("등록된 도시목록을 전부 조회할 수 있다")
    void getCities() {
        // given
        when(tariffRepository.findAllCities()).thenReturn(List.of("서울시", "부산시", "광주시"));

        // when
        List<String> cities = tariffService.getCities();

        // then
        verify(tariffRepository, times(1)).findAllCities();
        assertEquals(3, cities.size());
        assertTrue(cities.contains("서울시"));
        assertTrue(cities.contains("부산시"));
        assertTrue(cities.contains("광주시"));
    }

    @Test
    @DisplayName("도시이름으로 해당 도시에 등록된 업종을 전부 조회할 수 있다")
    void getSectors() {
        // given
        when(tariffRepository.findSectorsByCity("서울시")).thenReturn(List.of("가정용", "공업용"));

        // when
        List<String> sectors = tariffService.getSectors("서울시");

        // then
        verify(tariffRepository, times(1)).findSectorsByCity("서울시");
        assertEquals(2, sectors.size());
        assertTrue(sectors.contains("가정용"));
        assertTrue(sectors.contains("공업용"));
    }

    @Test
    @DisplayName("도시와 업종으로 요금정보를 전부 조회할 수 있다")
    void getTariffs() {
        // given
        when(tariffRepository.findTariffsByCityAndSector(cityName, sectorName))
                .thenReturn(List.of(tariff1, tariff2));

        // when
        List<Tariff> tariffs = tariffService.getTariffs(cityName, sectorName);

        // then
        verify(tariffRepository, times(1)).findTariffsByCityAndSector(cityName, sectorName);
        assertEquals(2, tariffs.size());
        assertTrue(tariffs.contains(tariff1));
        assertTrue(tariffs.contains(tariff2));
    }

    @Test
    @DisplayName("도시와 업종, 사용량에 해당하는 요금 구간을 조회하고 총 요금을 계산한다")
    void billTotal() {
        // given
        int usage = 20;
        when(tariffRepository.findTariffByVolume(cityName, sectorName, usage))
                .thenReturn(Optional.of(tariff1));

        // when
        Optional<PriceResponse> priceResponse = tariffService.billTotal(cityName, sectorName, usage);

        // then
        verify(tariffRepository, times(1)).findTariffByVolume(cityName, sectorName, usage);
        assertTrue(priceResponse.isPresent());
        assertEquals(1000 * 20, priceResponse.get().getUsagePrice());
    }

    @Test
    @DisplayName("usage에 해당하는 구간이 없으면 Optional.empty를 반환한다")
    void billTotalFail() {
        // given
        tariffRepository.save(tariff1);
        tariffRepository.save(tariff2);
        tariffRepository.save(tariff3);

        // when
        Optional<PriceResponse> result = tariffService.billTotal("서울시", "가정용", 9999);

        // then
        assertTrue(result.isEmpty());
        verify(tariffRepository).findTariffByVolume("서울시", "가정용", 9999);
    }
}