package com.verto.backendtask.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message)
    {
        super(message);
    }
}
