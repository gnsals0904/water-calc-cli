# Water Calc Cli
#### Spring shell 기반으로 만든 유저 인증 및 상수도 요금 계산 서비스입니다.
#### SpringBoot의 자동구성, 외부구성, AOP, Annotation, Logging, Test, 전략패턴 등을 적용한 프로젝트입니다.
**그 외의 디테일은 생략하였습니다.**


<br/>

## 사용언어 🛠
<img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=OpenJDK&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/></a>

## 구현기능 🧰
### 서버 `init`시, 데이터를 파싱해 메모리에 저장하는 기능 - 외부구성을 통해 자동화
1. `DataInitializer`클래스가 서버가 `init`되면, `dataParser interface`를 통해서 계정정보와 수도요금 정보를 메모리에 저장
```java
@Slf4j
@Component
@RequiredArgsConstructor
@Order(InteractiveShellRunner.PRECEDENCE - 1)
public class DataInitializer implements ApplicationRunner {

    private final DataParser dataParser;
    private final AccountRepository accountRepository;
    private final TariffRepository tariffRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Account> accounts = dataParser.loadAccounts();
        accounts.forEach(accountRepository::save);

        List<Tariff> tariffs = dataParser.loadTariffs();
        tariffs.forEach(tariffRepository::save);
    }
}
```
- `DataInitializer` 클래스는 `SpringShell`보다 먼저 시작되어야하므로 `Order Annotation`을 통해서 높은 우선순위를 갖도록 설정

<br>

2. ` DataParser` 인터페이스는 `CsvDataParser` 와 `JsonDataParser` 두가지 구현체를 가짐

```java
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "file.type", havingValue = "json")
public class JsonDataParser implements DataParser {
}

```

```java
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "file.type", havingValue = "csv")
public class CsvDataParser implements DataParser {
}
```
- 위 두 구현체는 `SpringBoot`의 기능 중, 외부 구성 기능을 통해 `application.properties`에 등록된 값을 통해 둘 중 하나의 구현체만 `Bean`으로 등록
- 이를 통해, `Json`형식의 데이터를 파싱하는 구현체를 로딩할지, `CSV`형식의 데이터를 파싱하는 구현체를 로딩할지 정할 수 있음

<br>

3. `DataParser`의 구현체를 통해 로딩된 데이터는 각 `Repository`에 저장됨
```java
public interface AccountRepository {
    void save(Account account);
    Optional<Account> findByIdAndPw(long id, String password);
    void removeAll();
}
```
```java
public interface TariffRepository {
    void save(Tariff tariff);
    List<String> findAllCities();
    List<String> findSectorsByCity(String city);
    List<Tariff> findTariffsByCityAndSector(String city, String sector);
    Optional<Tariff> findTariffByVolume(String city, String sector, int volume);
    void removeAll();
}
```
- 위 두 `Repository`는 프로젝트의 간략화를 위해 데이터를 메모리(`HashMap`)에만 저장
- 추후, 데이터베이스로 변환할 가능성이 있으므로 `Interface`로 추상화하고 실제 구현체는 따로 구현
```java
@Repository
public class InMemoryAccountRepo implements AccountRepository {
    // ...메서드들...
}
```

```java
@Repository
public class InMemoryTariffRepository implements TariffRepository {
    // ...메서드들...
}
```

<br>

### 유저 로그인 & 로그아웃 `logging`기능
#### 사용자가 Shell에 `login userId userPW`를 입력하면 로그인된다.
1. `WaterClacCommands`클래스에서 사용자의 입력을 받는다.
```java
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
}
```
2. `AuthenticationService`클래스에서 `Repository`를 조회 후 동작
```java
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;

    @Getter
    private Optional<Account> currentAccount = Optional.empty();

    @LogLoginAction(value = "login")
    public Account login(Long id, String password) {
        Account account = accountRepository.findByIdAndPw(id, password)
                .orElseThrow(() -> new NotFoundAccountException(id));
        currentAccount = Optional.of(account);
        return account;
    }

    @LogLoginAction(value = "logout")
    public void logout() {
        currentAccount = Optional.empty();
    }
}
```
- 이때, 해당 기능은 `Slf4j` 구현체인 `logback`과 `AOP`, `Annotation`을 통해 로깅
#### LogLoginAction Annotation
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogLoginAction {
    String value() default "";
}
```
#### AccountAop
```java
@Slf4j
@Aspect
@Component
public class AccountAop {

    @Around("@annotation(logAnno)")
    public Object logAccountAction(ProceedingJoinPoint joinPoint, LogLoginAction logAnno) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String action = logAnno.value();

        if(action.equals("login")){
            log.info("login([{}, {}])", args[0], args[1]);
        }
        else if(action.equals("logout")){
            log.info("logout([])");
        }

        return joinPoint.proceed();
    }

}
```
#### logback setting
```xml
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="ACCOUNT_FILE" class="ch.qos.logback.core.FileAppender">
        <file>account.log</file>
        <append>true</append>
        <encoder>
            <pattern>%date %level [%thread] %logger{10} [%file:%line] %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.vutz.watercalccli.account.aop.AccountAop" level="info" additivity="false">
        <appender-ref ref="ACCOUNT_FILE"/>
    </logger>

    <root level="info">
        <appender-ref ref="STDOUT"/>
    </root>

</configuration>
```

### 비슷한 방법으로 수도 요금 조회시 `AOP`를 통해 유저의 요청 및 서버 응답을 `logging` 합니다
`TariffAop` - 수도 요금 메서드의 실행 전 후를 잡아 로깅하는 클래스
```java
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TariffAop {

    private final AuthenticationService authenticationService;

    @Around("@annotation(logAnno)")
    public Object logTariffAction(ProceedingJoinPoint joinPoint, LogTariffAction logAnno) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String action = logAnno.value();
        Object result = joinPoint.proceed();
        if(logAnno.secure()){
            Optional<Account> currentAccount = authenticationService.getCurrentAccount();
            if (currentAccount.isEmpty()) {
                throw new NotLoggedInException();
            }
            String userName = currentAccount.get().getName();
            String arguments = Arrays.stream(args)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            log.info("<--- User name : {} called action : {} with args : {} ---", userName, action, arguments);
            log.info("--- User name : {} action : {} result : {} --->", userName, action, result);
        }
        return result;
    }
}
```

### `SpringBoot - profile`기능을 통해 사용자에게 한글 또는 영어로 응답 - 전략패턴
해당 메서드에서 `OutPutFormatter` `interface`를 통해 사용자에게 응답
```java
    @ShellMethod
    public String billTotal(String city, String sector, int usage) {
        Optional<PriceResponse> priceResponse = tariffService.billTotal(city, sector, usage);
        if(priceResponse.isPresent()){
            PriceResponse pr = priceResponse.get();
            return outPutFormatter.format(pr.getTariff(), pr.getUsagePrice());
        }
        return outPutFormatter.error();
    }
```

`OutPutFormatter Interface`는 `KoreanOutputFormatter`와 `EnglishOutputFormatter`로 구현됩니다.
```java
@Component
@Profile("eng")
public class EnglishOutputFormatter implements OutPutFormatter {

    @Override
    public String format(Tariff tariff, int usagePrice) {
        return String.format("city: %s, sector: %s, unit price(won): %d, bill total(won): %d",
                tariff.getCity(),
                tariff.getSector(),
                tariff.getUnitPrice(),
                usagePrice);
    }

    @Override
    public String error() {
        return "Unable to calculate the bill";
    }
}
```
```java
@Component
@Profile("default")
public class KoreanOutputFormatter implements OutPutFormatter {

    @Override
    public String format(Tariff tariff, int usagePrice) {
        return String.format("지자체명: %s, 업종: %s, 구간금액(원): %d, 총금액(원): %d",
                tariff.getCity(),
                tariff.getSector(),
                tariff.getUnitPrice(),
                usagePrice);
    }

    @Override
    public String error() {
        return "해당 요금을 계산할 수 없습니다";
    }
}
```
이후, 서버 `init`시에 `default` 또는 `eng` `arg`를 전달함으로써 서버는 어떤 형태로 사용자에게 응답할지 선택하게 됩니다.

### `SonarQube`를 통한 코드 품질 검사 및 `TestCoverage` 80% 달성
- `Mock`과 `Spy`등을 이용하였고, 단위테스트와 통합테스트를 진행하였습니다.