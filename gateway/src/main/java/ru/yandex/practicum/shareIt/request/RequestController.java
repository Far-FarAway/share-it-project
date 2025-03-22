package ru.yandex.practicum.shareIt.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.request.dto.RequestReqDto;

import java.util.List;

@RequestMapping("/requests")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestClient client;

    @PostMapping
    public ResponseEntity<RequestReqDto> postRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @Validated @RequestBody RequestReqDto dto) {
        log.info("Post request {}, userId={}", dto, userId);
        return client.postRequest(userId, dto);
    }

    @GetMapping
    public ResponseEntity<List<RequestReqDto>> getUserRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get requests of user with id {}", userId);
        return client.getUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RequestReqDto>> getAllRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get all requests except user with id {}", userId);
        return client.getAllRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<RequestReqDto> getRequestById(@PathVariable long requestId) {
        log.info("Get request with id {}", requestId);
        return client.getRequestById(requestId);
    }
}
