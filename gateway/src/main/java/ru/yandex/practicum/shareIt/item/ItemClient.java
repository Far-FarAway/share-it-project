package ru.yandex.practicum.shareIt.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.yandex.practicum.shareIt.client.BaseClient;
import ru.yandex.practicum.shareIt.item.dto.CommentRequestDto;
import ru.yandex.practicum.shareIt.item.dto.ItemRequestDto;

import java.util.List;
import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<ItemRequestDto> postItem(long userId, ItemRequestDto dto) {
        return post("", userId, dto);
    }

    public ResponseEntity<ItemRequestDto> updateItem(long userId, long itemId, ItemRequestDto dto) {
        return patch("/" + itemId, userId, dto);
    }

    public ResponseEntity<ItemRequestDto> getItem(long itemId) {
        return get("/" + itemId);
    }

    public ResponseEntity<List<ItemRequestDto>> getUserItems(long userId) {
        return get("", userId);
    }

    public ResponseEntity<List<ItemRequestDto>> itemSearch(String text) {
        Map<String, Object> params = Map.of("text", text);
        return get("/search?text={text}", null, params);
    }

    public ResponseEntity<ItemRequestDto> deleteItem(long userId, long itemId) {
        return delete("/" + itemId, userId);
    }

    public ResponseEntity<CommentRequestDto> addComment(long userId, long itemId, CommentRequestDto dto) {
        return post("/" + itemId + "/comment", userId, dto);
    }
}
