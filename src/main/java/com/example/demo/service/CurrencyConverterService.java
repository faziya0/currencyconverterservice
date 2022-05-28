package com.example.demo.service;

import com.example.demo.model.ExchangeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Objects;


@Service
@EnableScheduling
@RequiredArgsConstructor
public class CurrencyConverterService {
    @Value("${access_key}")
    private String ACCESS_KEY;
    private final String BASE_URL = "https://api.apilayer.com/";
    private final CacheManager cacheManager;
    @Cacheable("currentcycache")
    public ExchangeModel currencyConverter(String to,String from,String amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("apikey", ACCESS_KEY);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ExchangeModel> exchange = restTemplate.exchange(BASE_URL + "currency_data/convert?to=" + to + "&from=" + from + "&amount=" + amount,
                HttpMethod.GET, entity, ExchangeModel.class);

        return exchange.getBody();

    }

    @Scheduled(fixedRate = 60*1000)
    public void clearcache(){
    Objects.requireNonNull(cacheManager.getCache("currentcycache")).clear();

    }
}
