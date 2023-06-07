package ru.practicum.shareit.exception;

public class BookingRequestStatusException extends RuntimeException {
    public BookingRequestStatusException(String message) {
        super(message);
    }
}
