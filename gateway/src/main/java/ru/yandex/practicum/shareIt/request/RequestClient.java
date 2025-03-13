package ru.yandex.practicum.shareIt.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yandex.practicum.shareIt.client.BaseClient;
import ru.yandex.practicum.shareIt.request.dto.RequestReqDto;

import java.util.List;

@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<RequestReqDto> postRequest(long userId, RequestReqDto dto) {
        return post("", userId, dto);
    }

    public ResponseEntity<List<RequestReqDto>> getUserRequests(long userId) {
        return get("", userId);
    }

    public ResponseEntity<List<RequestReqDto>> getAllRequests(long userId) {
        return get("/all", userId);
    }

    public ResponseEntity<RequestReqDto> getRequestById(long requestId) {
        return get("/" + requestId);
    }
}
