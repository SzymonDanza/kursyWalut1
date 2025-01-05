package com.kursywalut.kursywalut.service;


import com.kursywalut.kursywalut.model.CurrencyRate;
import com.kursywalut.kursywalut.model.NBPResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CurrencyService {
    private static final String NBP_API_URL = "https://api.nbp.pl/api/exchangerates/tables/A?format=json";

    public List<CurrencyRate> getCurrencyRates() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Pobieranie danych z API
            NBPResponse[] response = restTemplate.getForObject(NBP_API_URL, NBPResponse[].class);
            // Zwrócenie listy kursów
            return response[0].getRates();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching data from NBP API", e);
        }
    }
}
