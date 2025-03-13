package ru.yandex.practicum.shareIt.request.service;

import ru.yandex.practicum.shareIt.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto postRequest(RequestDto dto, long userId);

    List<RequestDto> getUserRequests(long userId);

    List<RequestDto> getAllRequests(long userId);

    RequestDto getRequestById(long requestId);
}
