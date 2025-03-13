package ru.yandex.practicum.shareIt.exception;

public class SameEmailException extends RuntimeException {
    public SameEmailException(String message) {
        super(message);
    }
}
