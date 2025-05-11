package com.vutz.watercalccli.shell;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.service.AuthenticationService;
import com.vutz.watercalccli.tariff.dto.PriceResponse;
import com.vutz.watercalccli.tariff.dto.Tariff;
import com.vutz.watercalccli.tariff.formatter.OutPutFormatter;
import com.vutz.watercalccli.tariff.service.TariffService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class WaterCalcCommands {

    private final TariffService tariffService;
    private final AuthenticationService authenticationService;
    private final OutPutFormatter outPutFormatter;

    @ShellMethod
    public String login(long id, String password) {
        Account login = authenticationService.login(id, password);
        return login.toString();
    }

    @ShellMethod
    public String logout() {
        authenticationService.logout();
        return "good bye";
    }

    @ShellMethod
    public String currentUser() {
        return authenticationService.getCurrentAccount()
                .map(Account::toString)
                .orElse("현재 로그인된 유저가 없습니다");
    }

    @ShellMethod
    public String city() {
        List<String> cities = tariffService.getCities();
        if(cities.isEmpty()){
            return "서비스에 등록된 도시가 없습니다";
        }
        return cities.toString();
    }

    @ShellMethod
    public String sector(String city) {
        List<String> sectors = tariffService.getSectors(city);
        if(sectors.isEmpty()){
            return "해당 도시에 등록된 업종이 없습니다";
        }
        return sectors.toString();
    }

    @ShellMethod
    public String price(String city, String sector) {
        List<Tariff> tariffs = tariffService.getTariffs(city, sector);
        if(tariffs.isEmpty()){
            return "해당 도시와 업종에는 요금 정보가 등록되어 있지 않습니다";
        }
        return tariffs.toString();
    }

    @ShellMethod
    public String billTotal(String city, String sector, int usage) {
        Optional<PriceResponse> priceResponse = tariffService.billTotal(city, sector, usage);
        if(priceResponse.isPresent()){
            PriceResponse pr = priceResponse.get();
            return outPutFormatter.format(pr.getTariff(), pr.getUsagePrice());
        }
        return outPutFormatter.error();
    }

}
