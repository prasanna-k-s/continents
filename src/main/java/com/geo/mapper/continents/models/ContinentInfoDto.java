package com.geo.mapper.continents.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContinentInfoDto {
    @JsonProperty(value="countries")
    private List<String> inputCountries;
    @JsonProperty(value = "name")
    private String nameOfTheContinent;
    @JsonProperty(value="otherCountries")
    private List<String> otherCountries;

    public List<String> getInputCountries() {
        return inputCountries;
    }

    public void setInputCountries(List<String> inputCountries) {
        this.inputCountries = inputCountries;
    }

    public String getNameOfTheContinent() {
        return nameOfTheContinent;
    }

    public void setNameOfTheContinent(String nameOfTheContinent) {
        this.nameOfTheContinent = nameOfTheContinent;
    }

    public List<String> getOtherCountries() {
        return otherCountries;
    }

    public void setOtherCountries(List<String> otherCountries) {
        this.otherCountries = otherCountries;
    }
}
