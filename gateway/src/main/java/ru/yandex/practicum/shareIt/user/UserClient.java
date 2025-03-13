package ru.yandex.practicum.shareIt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yandex.practicum.shareIt.client.BaseClient;
import ru.yandex.practicum.shareIt.user.dto.UserRequestDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<UserRequestDto> saveUser(UserRequestDto dto) {
        return post("", dto);
    }

    public ResponseEntity<UserRequestDto> getUser(long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<UserRequestDto> updateUser(long userId, UserRequestDto dto) {
        return patch("/" + userId, dto);
    }

    public ResponseEntity<UserRequestDto> deleteUser(long userId) {
        return delete("/" + userId);
    }
}
