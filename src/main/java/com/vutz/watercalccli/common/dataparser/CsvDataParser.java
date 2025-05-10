package com.vutz.watercalccli.common.dataparser;

import com.vutz.watercalccli.account.dto.Account;
import com.vutz.watercalccli.common.properties.FileProperties;
import com.vutz.watercalccli.tariff.dto.Tariff;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "file.type", havingValue = "csv")
public class CsvDataParser implements DataParser {

    private final FileProperties fileProperties;

    @Override
    public List<Tariff> loadTariffs() {
        List<Tariff> tariffs = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .setDelimiter(',')
                .get();

        try (
                InputStream input = new ClassPathResource(fileProperties.getPricePath()).getInputStream();
                Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
        ) {
            Iterable<CSVRecord> records = csvFormat.parse(reader);
            for (CSVRecord record : records) {
                long id = Long.parseLong(record.get("순번"));
                String city = record.get("지자체명");
                String sector = record.get("업종");
                int sectionStart = Integer.parseInt(record.get("구간시작(세제곱미터)"));
                int sectionEnd = Integer.parseInt(record.get("구간끝(세제곱미터)"));
                int unitPrice = Integer.parseInt(record.get("구간금액(원)"));

                tariffs.add(new Tariff(id, city, sector, sectionStart, sectionEnd, unitPrice));
            }
        } catch (IOException e) {
            throw new IllegalStateException("CSV 요금 파일 파싱 실패", e);
        }

        return tariffs;
    }

    @Override
    public List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .setDelimiter(',')
                .get();

        try (
                InputStream input = new ClassPathResource(fileProperties.getAccountPath()).getInputStream();
                Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
        ) {
            Iterable<CSVRecord> records = csvFormat.parse(reader);
            for (CSVRecord record : records) {
                long id = Long.parseLong(record.get("아이디"));
                String password = record.get("비밀번호");
                String name = record.get("이름");
                accounts.add(new Account(id, password, name));
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return accounts;
    }
}
