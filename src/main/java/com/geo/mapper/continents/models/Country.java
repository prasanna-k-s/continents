package com.geo.mapper.continents.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class Country {
    @JsonProperty
    private String code;
    @JsonProperty
    private String name;
    @JsonProperty
    private String nativeCode;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String capital;
    @JsonProperty
    private String currency;
    @JsonProperty
    private String emoji;
    @JsonProperty
    private String emojiU;

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

    public String getNativeCode() {
        return nativeCode;
    }

    public void setNativeCode(String nativeCode) {
        this.nativeCode = nativeCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmojiU() {
        return emojiU;
    }

    public void setEmojiU(String emojiU) {
        this.emojiU = emojiU;
    }


}
