package com.buddiend.buddiend.models.exceptions;

public class VideoChatNotFoundException extends RuntimeException{
    public VideoChatNotFoundException() {
        super("Video chat with the provided meeting id hasn't been found!");
    }
}
