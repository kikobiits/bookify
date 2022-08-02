package com.example.bookify.model.dto.comment;

public class CommentMessageDTO {

    private String message;

    public CommentMessageDTO(String message) {
        this.message = message;
    }

    public CommentMessageDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
