package com.vutz.watercalccli.shell;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.account.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class WaterCalcCommands {

    private final AuthenticationService authenticationService;

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
                .orElse("");
    }

    @ShellMethod
    public String city() {
        return null;
    }

    @ShellMethod
    public String sector(String city) {
        return null;
    }

    @ShellMethod
    public String price(String city, String sector) {
        return null;
    }

    @ShellMethod
    public String billTotal(String city, String sector, int usage) {
        return null;
    }

}
