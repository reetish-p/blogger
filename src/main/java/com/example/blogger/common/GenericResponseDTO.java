package com.example.blogger.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenericResponseDTO {
    String message;
    String timestamp;
    {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        timestamp = currentLocalDateTime.format(dateTimeFormatter);
    }
    public GenericResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
