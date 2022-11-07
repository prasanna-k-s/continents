package com.geo.mapper.continents.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Continent {
    @JsonProperty
    private String code;
    @JsonProperty
    private String name;
    @JsonProperty
    private List<Country> countries;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
