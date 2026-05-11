package com.sales.exception;

import java.time.LocalDateTime;

public class ErrorMessage {
    public String message;
    public int status;
    public LocalDateTime timestamp;

    public ErrorMessage(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}