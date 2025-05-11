package com.vutz.watercalccli.common.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vutz.watercalccli.tariff.dto.Tariff;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("eng")
@DisplayName("EnglishOutputFormatter 테스트")
class EnglishOutputFormatterTest {

    @Autowired
    OutPutFormatter formatter;

    @Test
    @DisplayName("profile에 따라 영문으로 format이 출력되어야 한다")
    void testFormat() {
        Tariff tariff = new Tariff(1L, "서울시", "가정용", 1, 30, 100);
        String result = formatter.format(tariff, 3000);

        assertTrue(result.contains("city: 서울시"));
        assertTrue(result.contains("bill total(won): 3000"));
    }

    @Test
    @DisplayName("요금을 계산할 수 없다면 영문 오류 메시지가 반환된다")
    void testError() {
        assertEquals("Unable to calculate the bill", formatter.error());
    }
}