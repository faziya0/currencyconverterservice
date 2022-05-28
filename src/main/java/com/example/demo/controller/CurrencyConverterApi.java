package com.example.demo.controller;

import com.example.demo.model.ExchangeModel;
import com.example.demo.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class CurrencyConverterApi {

   private final CurrencyConverterService currencyConverterService;

    @GetMapping("api/currency-exchange/from/{from}/to/{to}")
    public ResponseEntity<ExchangeModel> currencyConverter(@PathVariable String from, @PathVariable String to, @RequestParam String amount)  {
        ExchangeModel exchangeModel = currencyConverterService.currencyConverter(to, from, amount);
        return ResponseEntity.ok(exchangeModel);

    }


    }

