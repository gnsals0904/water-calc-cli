package com.vutz.watercalccli;

import com.vutz.watercalccli.common.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class WaterCalcCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaterCalcCliApplication.class, args);
    }

}
