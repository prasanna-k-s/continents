package com.geo.mapper.continents.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data{

    @JsonProperty
    private Continent continent;

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }
}
