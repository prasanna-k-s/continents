package com.geo.mapper.continents.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String errorMessage;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
