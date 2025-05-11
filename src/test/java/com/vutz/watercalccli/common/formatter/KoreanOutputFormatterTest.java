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
@ActiveProfiles("default")
@DisplayName("KoreanOutputFormatter 테스트")
class KoreanOutputFormatterTest {

    @Autowired
    OutPutFormatter formatter;

    @Test
    @DisplayName("profile에 따라 한글로 format이 출력되어야 한다")
    void format() {
        Tariff tariff = new Tariff(1L, "서울시", "가정용", 1, 30, 100);
        String result = formatter.format(tariff, 4500);

        assertTrue(result.contains("지자체명: 서울시"));
        assertTrue(result.contains("총금액(원): 4500"));
    }

    @Test
    @DisplayName("요금을 계산할 수 없다면 한글 오류 메시지가 반환된다")
    void error() {
        assertEquals("해당 요금을 계산할 수 없습니다", formatter.error());
    }
}