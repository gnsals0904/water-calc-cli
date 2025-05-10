package com.vutz.watercalccli.common.dataparser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vutz.watercalccli.account.dto.AccountJsonDto;
import com.vutz.watercalccli.common.properties.FileProperties;
import com.vutz.watercalccli.price.dto.Price;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "file.type", havingValue = "json")
@RequiredArgsConstructor
public class JsonDataParser implements DataParser {

    private final ObjectMapper mapper;
    private final FileProperties fileProperties;

    @Override
    public List<String> cities() {
        return List.of();
    }

    @Override
    public List<String> sectors(String city) {
        return List.of();
    }

    @Override
    public Price price(String city, String sector) {
        return null;
    }

    @Override
    public List<AccountJsonDto> accounts() {
        try {
            Resource resource = new ClassPathResource(fileProperties.getAccountPath());
            return mapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<AccountJsonDto>>() {}
            );
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

