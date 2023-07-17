package com.techelevator.service;

import com.techelevator.model.StateSalesTax;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class TaxService {

    private static final String API_BASE_URL = "https://teapi.netlify.app/api/statetax?state=";

    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getSalesTaxForState(String stateCode) {
        StateSalesTax salesTax = null;
        try {
            salesTax = restTemplate.getForObject(API_BASE_URL + stateCode, StateSalesTax.class);
        } catch (RestClientResponseException e) {
            System.out.println(e.getMessage());
        }
        assert salesTax != null;
        return salesTax.getSalesTax();
    }

}
