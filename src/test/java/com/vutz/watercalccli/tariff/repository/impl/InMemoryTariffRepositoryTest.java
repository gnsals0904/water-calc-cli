package com.vutz.watercalccli.tariff.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vutz.watercalccli.tariff.dto.Tariff;
import com.vutz.watercalccli.tariff.repository.TariffRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InMemoryTariffRepository 단위 테스트")
class InMemoryTariffRepositoryTest {

    TariffRepository tariffRepository = new InMemoryTariffRepository();

    Tariff tariff1 = new Tariff(1, "서울시", "가정용", 10, 50, 1000);
    Tariff tariff2 = new Tariff(2, "서울시", "가정용", 50, 100, 2000);
    Tariff tariff3 = new Tariff(3, "서울시", "공업용", 100, 150, 3000);
    Tariff tariff4 = new Tariff(4, "부산시", "가정용", 100, 150, 3000);
    Tariff tariff5 = new Tariff(5, "광주시", "가정용", 100, 150, 3000);

    @BeforeEach()
    void before() {
        tariffRepository.removeAll();
        tariffRepository.save(tariff1);
        tariffRepository.save(tariff2);
        tariffRepository.save(tariff3);
        tariffRepository.save(tariff4);
        tariffRepository.save(tariff5);
    }

    @Test
    @DisplayName("저장된 도시 목록을 전부 조회할 수 있다")
    void findAllCities() {
        List<String> cities = tariffRepository.findAllCities();
        assertTrue(cities.contains("서울시"));
        assertTrue(cities.contains("부산시"));
        assertTrue(cities.contains("광주시"));
        assertEquals(3, cities.size());
    }

    @Test
    @DisplayName("특정 도시에 해당하는 업종 목록을 전부 조회할 수 있다")
    void findSectorsByCity() {
        List<String> sectors = tariffRepository.findSectorsByCity("서울시");
        assertTrue(sectors.contains("가정용"));
        assertTrue(sectors.contains("공업용"));
        assertEquals(2, sectors.size());
    }

    @Test
    @DisplayName("도시와 업종 이름으로 요금 목록을 조회할 수 있다")
    void findTariffsByCityAndSector() {
        List<Tariff> tariffs = tariffRepository.findTariffsByCityAndSector("서울시", "가정용");
        assertEquals(2, tariffs.size());
        assertTrue(tariffs.contains(tariff1));
        assertTrue(tariffs.contains(tariff2));
    }

    @Test
    @DisplayName("사용량에 해당하는 요금 구간을 찾을 수 있다")
    void findTariffByVolume() {
        Optional<Tariff> result = tariffRepository.findTariffByVolume("서울시", "가정용", 15);
        assertTrue(result.isPresent());
        assertEquals(tariff1, result.get());

        Optional<Tariff> result2 = tariffRepository.findTariffByVolume("서울시", "가정용", 65);
        assertTrue(result2.isPresent());
        assertEquals(tariff2, result2.get());
    }

    @Test
    @DisplayName("해당 조건에 맞는 요금이 없으면 비어있는 Optional 객체를 반환한다")
    void findTariffByVolume_NotFound() {
        Optional<Tariff> result = tariffRepository.findTariffByVolume("서울시", "가정용", 1000);
        assertTrue(result.isEmpty());
    }
}