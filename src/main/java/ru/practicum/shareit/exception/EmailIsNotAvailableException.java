package ru.practicum.shareit.exception;

public class EmailIsNotAvailableException extends RuntimeException {
    public EmailIsNotAvailableException(String message) {
        super(message);
    }
}
