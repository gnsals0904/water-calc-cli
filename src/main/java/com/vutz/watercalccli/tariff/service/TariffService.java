package com.vutz.watercalccli.tariff.service;

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

    public List<String> getCities() {
        return tariffRepository.findAllCities();
    }

    public List<String> getSectors(String city) {
        return tariffRepository.findSectorsByCity(city);
    }

    public List<Tariff> getTariffs(String city, String sector) {
        return tariffRepository.findTariffsByCityAndSector(city, sector);
    }

    public Optional<PriceResponse> billTotal(String city, String sector, int usage) {
        Optional<Tariff> tariffByVolume = tariffRepository.findTariffByVolume(city, sector, usage);
        if(tariffByVolume.isPresent()){
            Tariff tariff = tariffByVolume.get();
            return Optional.of(new PriceResponse(tariff, tariff.calcUsagePrice(usage)));
        }
        return Optional.empty();
    }
}
