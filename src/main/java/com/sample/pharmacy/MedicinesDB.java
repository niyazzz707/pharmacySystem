package com.sample.pharmacy;

public class MedicinesDB {

    private Integer idDB;
    private String medNameDB;
    private Integer quantityDB;
    private Integer priceDB;

    public MedicinesDB(Integer idDB, String medNameDB, Integer quantityDB, Integer priceDB) {
        this.idDB = idDB;
        this.medNameDB = medNameDB;
        this.quantityDB = quantityDB;
        this.priceDB = priceDB;
    }

    public Integer getIdDB() {
        return idDB;
    }

    public void setIdDB(Integer idDB) {
        this.idDB = idDB;
    }

    public String getMedNameDB() {
        return medNameDB;
    }

    public void setMedNameDB(String medNameDB) {
        this.medNameDB = medNameDB;
    }

    public Integer getQuantityDB() {
        return quantityDB;
    }

    public void setQuantityDB(Integer quantityDB) {
        this.quantityDB = quantityDB;
    }

    public Integer getPriceDB() {
        return priceDB;
    }

    public void setPriceDB(Integer priceDB) {
        this.priceDB = priceDB;
    }
}
