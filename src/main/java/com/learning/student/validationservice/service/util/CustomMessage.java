package com.learning.student.validationservice.service.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMessage {
    private final String message;
    private final String description;

    public CustomMessage(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
