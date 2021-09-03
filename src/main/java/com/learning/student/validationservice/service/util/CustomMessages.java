package com.learning.student.validationservice.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomMessages {
    private final List<CustomMessage> messages = Collections.synchronizedList(new ArrayList<>());

    public Collection<CustomMessage> getMessages() {
        return Collections.unmodifiableCollection(messages);
    }

    public void addMessage(String field, String message, String description) {
        this.messages.add(new CustomMessage(field, message, description));
    }

    public boolean hasErrors() {
        return !this.messages.isEmpty();
    }
}
