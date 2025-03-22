package ru.yandex.practicum.shareIt.exception;

public class ConditionsNotMatchException extends RuntimeException {
    public ConditionsNotMatchException(String message) {
        super(message);
    }
}
