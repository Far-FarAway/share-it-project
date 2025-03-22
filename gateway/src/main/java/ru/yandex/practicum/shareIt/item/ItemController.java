package ru.yandex.practicum.shareIt.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.item.dto.CommentRequestDto;
import ru.yandex.practicum.shareIt.item.dto.ItemRequestDto;
import ru.yandex.practicum.shareIt.marker.OnCreate;

import java.util.List;

@Controller
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient client;

    @PostMapping
    public ResponseEntity<ItemRequestDto> postItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @Validated({OnCreate.class}) @RequestBody ItemRequestDto itemDto) {
        log.info("Post item {}, userId={}", itemDto, userId);
        return client.postItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemRequestDto> updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long itemId,
                                             @RequestBody ItemRequestDto itemDto) {
        log.info("Update item {}, userId={}, itemId={}", itemDto, userId, itemId);
        return client.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemRequestDto> getItem(@PathVariable long itemId) {
        log.info("Get item with id {}", itemId);
        return client.getItem(itemId);
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getUserItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get items of user with id {}", userId);
        return client.getUserItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemRequestDto>> itemSearch(@RequestParam String text) {
        log.info("Search item with text {}", text);
        return client.itemSearch(text);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ItemRequestDto> deleteItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long itemId) {
        log.info("Delete user's item with id {}, userId={}", itemId, userId);
        return client.deleteItem(userId, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentRequestDto> addComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long itemId,
                                             @RequestBody CommentRequestDto commentDto) {
        log.info("Post comment {}, itemId={}, userId={}", commentDto, itemId, userId);
        return client.addComment(userId, itemId, commentDto);
    }
}
