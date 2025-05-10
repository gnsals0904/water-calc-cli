package com.vutz.watercalccli.common.dataparser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.dto.AccountJsonDto;
import com.vutz.watercalccli.common.properties.FileProperties;
import com.vutz.watercalccli.tariff.dto.Tariff;
import com.vutz.watercalccli.tariff.dto.TariffJsonDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "file.type", havingValue = "json")
public class JsonDataParser implements DataParser {

    private final ObjectMapper mapper;
    private final FileProperties fileProperties;

    @Override
    public List<Tariff> loadTariffs() {
        try {
            Resource resource = new ClassPathResource(fileProperties.getPricePath());
            List<TariffJsonDto> tariffJsonDtos = mapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<TariffJsonDto>>() {}
            );

            return tariffJsonDtos.stream()
                    .map(TariffJsonDto::toTariff)
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Account> loadAccounts() {
        try {
            Resource resource = new ClassPathResource(fileProperties.getAccountPath());
            List<AccountJsonDto> accountJsonDtos = mapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<AccountJsonDto>>() {}
            );

            return accountJsonDtos.stream()
                    .map(AccountJsonDto::toAccount)
                    .toList();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

