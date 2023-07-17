package com.techelevator.model;

import java.math.BigDecimal;

public class StateSalesTax {

    private BigDecimal salesTax;
    private String lastUpdated;

    public StateSalesTax() {}

    public StateSalesTax(BigDecimal salesTax, String lastUpdated) {
        this.salesTax = salesTax;
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
