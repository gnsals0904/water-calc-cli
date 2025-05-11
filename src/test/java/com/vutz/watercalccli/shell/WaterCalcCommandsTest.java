package com.vutz.watercalccli.shell;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vutz.watercalccli.account.exception.NotLoggedInException;
import com.vutz.watercalccli.account.service.AuthenticationService;
import com.vutz.watercalccli.tariff.service.TariffService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("default")
@DisplayName("WaterCalcCommands CLI 명령어 테스트")
class WaterCalcCommandsTest {

    @Autowired
    WaterCalcCommands commands;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TariffService tariffService;

    @Test
    @DisplayName("currentUser 명령어는 로그인 유저가 없다면 적절한 메시지를 반환한다")
    void testCurrentUserWithoutLogin() {
        authenticationService.logout();
        String result = commands.currentUser();

        assertEquals("현재 로그인된 유저가 없습니다", result);
    }

    @Test
    @DisplayName("currentUser 명령어는 로그인 유저 정보를 반환한다")
    void testCurrentUserWithLogin() {
        authenticationService.login(1L, "1");

        String result = commands.currentUser();
        assertTrue(result.contains("hunmin"));
    }

    @Test
    @DisplayName("logout 명령어는 'good bye' 메시지를 반환한다")
    void testLogout() {
        String result = commands.logout();

        assertEquals("good bye", result);
    }

    @Test
    @DisplayName("로그인을 하지 않으면 요금 관련 명령어를 입력했을 때, 예외가 발생한다")
    void testCityCommandWithNotLogin() {
        commands.logout();
        assertAll(
                () -> assertThrows(NotLoggedInException.class, () -> commands.city()),
                () -> assertThrows(NotLoggedInException.class, () -> commands.sector("test")),
                () -> assertThrows(NotLoggedInException.class, () -> commands.price("test", "test")),
                () -> assertThrows(NotLoggedInException.class, () -> commands.billTotal("test", "test", 1))

        );
    }

    @Test
    @DisplayName("로그인 후, city 명령어는 도시 리스트를 반환한다")
    void testCityCommandWithLogin() {
        authenticationService.login(1L, "1");
        String result = commands.city();

        assertTrue(result.contains("동두천시"));
    }

    @Test
    @DisplayName("해당 도시에 등록된 업종이 없으면 안내 메시지를 반환한다")
    void testSectorEmpty() {
        authenticationService.login(1L, "1");
        String result = commands.sector("서울시");

        assertEquals("해당 도시에 등록된 업종이 없습니다", result);
    }

    @Test
    @DisplayName("price 명령어와 도시이름과 업종이름을 입력하면 요금 목록을 반환한다")
    void testPrice() {
        authenticationService.login(1L, "1");
        String result = commands.price("동두천시", "가정용");

        assertTrue(result.contains("동두천시"));
        assertTrue(result.contains("가정용"));
    }

    @Test
    @DisplayName("해당 도시와 업종이름에 등록된 요금 목록이 없으면 안내 메시지를 반환한다")
    void testPriceEmpty() {
        authenticationService.login(1L, "1");
        String result = commands.price("서울시", "가정용");

        assertEquals("해당 도시와 업종에는 요금 정보가 등록되어 있지 않습니다", result);
    }

    @Test
    @DisplayName("billTotal 명령어는 검색 구간에 해당하는 요금 계산 결과를 반환한다")
    void testBillTotal() {
        authenticationService.login(1L, "1");
        String result = commands.billTotal("동두천시", "가정용", 10);

        assertTrue(result.contains("동두천시"));
        assertTrue(result.contains("6900"));
    }

    @Test
    @DisplayName("billTotal 명령어는 검색 구간에 해당하는 요금이 없으면 포매터의 error 메시지를 반환한다")
    void testBillTotalEmpty() {
        authenticationService.login(1L, "1");
        String result = commands.billTotal("서울시", "가정용", 999);

        assertEquals("해당 요금을 계산할 수 없습니다", result);
    }


}