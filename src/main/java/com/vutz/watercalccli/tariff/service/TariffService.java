package com.vutz.watercalccli.tariff.service;

import com.vutz.watercalccli.tariff.annotation.LogTariffAction;
import com.vutz.watercalccli.tariff.dto.PriceResponse;
import com.vutz.watercalccli.tariff.dto.Tariff;
import com.vutz.watercalccli.tariff.repository.TariffRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffService {

    private final TariffRepository tariffRepository;

    @LogTariffAction(value = "city")
    public List<String> getCities() {
        return tariffRepository.findAllCities();
    }

    @LogTariffAction(value = "sector")
    public List<String> getSectors(String city) {
        return tariffRepository.findSectorsByCity(city);
    }

    @LogTariffAction(value = "price")
    public List<Tariff> getTariffs(String city, String sector) {
        return tariffRepository.findTariffsByCityAndSector(city, sector);
    }

    @LogTariffAction(value = "bill-total")
    public Optional<PriceResponse> billTotal(String city, String sector, int usage) {
        Optional<Tariff> tariffByVolume = tariffRepository.findTariffByVolume(city, sector, usage);
        if(tariffByVolume.isPresent()){
            Tariff tariff = tariffByVolume.get();
            return Optional.of(new PriceResponse(tariff, tariff.calcUsagePrice(usage)));
        }
        return Optional.empty();
    }
}
