package com.bpietrzak.stockmarket.exception;

public class InvalidOperationException  extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
