package com.vutz.watercalccli.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("file")
public class FileProperties {
    private final String type;
    private final String pricePath;
    private final String accountPath;
}
