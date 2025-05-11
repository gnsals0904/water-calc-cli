package com.vutz.watercalccli.tariff.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TariffJsonDto 기능 테스트")
class TariffJsonDtoTest {

    @Test
    @DisplayName("toTariff 메서드 호출 시 Tariff 객체로 변환된다")
    void toTariff() {
        // given
        long id = 1L;
        String city = "광주시";
        String sector = "가정용";
        int sectionStart = 1;
        int sectionEnd = 20;
        int unitPrice = 100;
        TariffJsonDto dto = new TariffJsonDto(id, city, sector, sectionStart, sectionEnd, unitPrice);

        // when
        Tariff tariff = dto.toTariff();

        // then
        assertEquals(1L, tariff.getId());
        assertEquals("광주시", tariff.getCity());
        assertEquals("가정용", tariff.getSector());
        assertEquals(1, tariff.getSectionStart());
        assertEquals(20, tariff.getSectionEnd());
        assertEquals(100, tariff.getUnitPrice());
    }
}