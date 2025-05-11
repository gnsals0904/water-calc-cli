package com.vutz.watercalccli.tariff.repository;

import com.vutz.watercalccli.tariff.dto.Tariff;
import java.util.List;
import java.util.Optional;

public interface TariffRepository {
    void save(Tariff tariff);
    List<String> findAllCities();
    List<String> findSectorsByCity(String city);
    List<Tariff> findTariffsByCityAndSector(String city, String sector);
    Optional<Tariff> findTariffByVolume(String city, String sector, int volume);
    void removeAll();
}
