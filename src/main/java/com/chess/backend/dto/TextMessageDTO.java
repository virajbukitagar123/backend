package com.chess.backend.dto;


public class TextMessageDTO {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TextMessageDTO(String message) {
        this.message = message;
    }
}