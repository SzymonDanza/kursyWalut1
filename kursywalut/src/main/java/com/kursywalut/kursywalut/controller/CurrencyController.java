package com.kursywalut.kursywalut.controller;


import com.kursywalut.kursywalut.model.CurrencyRate;
import com.kursywalut.kursywalut.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public String showCurrencyRates(Model model) {
        List<CurrencyRate> rates = currencyService.getCurrencyRates();
        model.addAttribute("rates", rates);
        return "currencyRates";
    }

    @GetMapping("/convert")
    public String convertCurrency(@RequestParam String from, @RequestParam String to,
                                  @RequestParam double amount, Model model) {
        List<CurrencyRate> rates = currencyService.getCurrencyRates();
    
        // Pobieranie kursów walut
        double fromRate = rates.stream()
                               .filter(rate -> rate.getCode().equals(from))
                               .findFirst()
                               .orElseThrow(() -> new RuntimeException("Currency not found: " + from))
                               .getMid();
        double toRate = rates.stream()
                             .filter(rate -> rate.getCode().equals(to))
                             .findFirst()
                             .orElseThrow(() -> new RuntimeException("Currency not found: " + to))
                             .getMid();
    
        // Poprawny wzór
        double result = (amount * fromRate) / toRate;
    
        // Dodanie danych do modelu
        model.addAttribute("result", result);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("amount", amount);
    
        return "conversionResult";
    }
    

}
