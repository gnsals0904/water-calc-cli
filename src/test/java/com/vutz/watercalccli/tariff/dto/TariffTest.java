package com.vutz.watercalccli.tariff.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tariff 기능 테스트")
class TariffTest {

    Tariff tariff = new Tariff(1L, "서울시", "가정용", 1, 30, 100);

    @Test
    @DisplayName("contains 메서드는 값이 구간 안에 있으면 true를 반환한다")
    void containsWithinRange() {
        assertTrue(tariff.contains(10));
        assertTrue(tariff.contains(1));
        assertTrue(tariff.contains(30));
    }

    @Test
    @DisplayName("contains 메서드는 값이 구간 밖이면 false를 반환한다")
    void containsOutsideRange() {
        assertFalse(tariff.contains(0));
        assertFalse(tariff.contains(31));
    }

    @Test
    @DisplayName("calcUsagePrice()는 단가 × 사용량을 계산한다")
    void calcUsagePrice() {
        assertEquals(1000, tariff.calcUsagePrice(10));
        assertEquals(0, tariff.calcUsagePrice(0));
    }
}