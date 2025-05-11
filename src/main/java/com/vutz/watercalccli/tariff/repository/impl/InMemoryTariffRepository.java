package com.vutz.watercalccli.tariff.repository.impl;

import com.vutz.watercalccli.tariff.dto.Tariff;
import com.vutz.watercalccli.tariff.repository.TariffRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTariffRepository implements TariffRepository {

    private final Map<String, Set<String>> cityAndSectorMap = new HashMap<>();
    private final Map<String, Map<String, List<Tariff>>> tariffMap = new HashMap<>();

    @Override
    public void save(Tariff tariff) {
        if (!tariffMap.containsKey(tariff.getCity())) {
            tariffMap.put(tariff.getCity(), new HashMap<>());
        }
        Map<String, List<Tariff>> tariffInfoMap = tariffMap.get(tariff.getCity());

        if (!tariffInfoMap.containsKey(tariff.getSector())) {
            tariffInfoMap.put(tariff.getSector(), new ArrayList<>());
        }
        tariffInfoMap.get(tariff.getSector()).add(tariff);

        if (!cityAndSectorMap.containsKey(tariff.getCity())) {
            cityAndSectorMap.put(tariff.getCity(), new HashSet<>());
        }
        cityAndSectorMap.get(tariff.getCity()).add(tariff.getSector());
    }

    @Override
    public List<String> findAllCities() {
        return new ArrayList<>(cityAndSectorMap.keySet());
    }

    @Override
    public List<String> findSectorsByCity(String city) {
        return new ArrayList<>(cityAndSectorMap.getOrDefault(city, Set.of()));
    }

    @Override
    public List<Tariff> findTariffsByCityAndSector(String city, String sector) {
        return tariffMap.getOrDefault(city, Map.of())
                .getOrDefault(sector, List.of());
    }

    @Override
    public Optional<Tariff> findTariffByVolume(String city, String sector, int volume) {
        return findTariffsByCityAndSector(city, sector).stream()
                .filter(t -> t.contains(volume))
                .findFirst();
    }

    @Override
    public void removeAll() {
        cityAndSectorMap.clear();
        tariffMap.clear();
    }
}
