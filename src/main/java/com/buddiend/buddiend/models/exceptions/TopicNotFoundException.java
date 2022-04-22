package com.buddiend.buddiend.models.exceptions;

public class TopicNotFoundException extends RuntimeException{

    public TopicNotFoundException(Long id) {
        super(String.format("Topic with id: %d is not found", id));

    }
}
