package com.geo.mapper.continents.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geo.mapper.continents.models.Data;

public class ResponseData {

    @JsonProperty
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

