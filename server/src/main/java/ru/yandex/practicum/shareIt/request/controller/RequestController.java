package ru.yandex.practicum.shareIt.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.request.dto.RequestDto;
import ru.yandex.practicum.shareIt.request.service.RequestService;

import java.util.List;

@Controller
@RequestMapping("/requests")
@RestController
@RequiredArgsConstructor
public class RequestController {
    private final RequestService service;

    @PostMapping
    public RequestDto postRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @RequestBody RequestDto dto) {
        return service.postRequest(dto, userId);
    }

    @GetMapping
    public List<RequestDto> getUserRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.getUserRequests(userId);
    }

    @GetMapping("/all")
    public List<RequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public RequestDto getRequestById(@PathVariable long requestId) {
        return service.getRequestById(requestId);
    }
}
