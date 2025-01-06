package ru.yandex.practicum.shareIt.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.service.ItemService;
import ru.yandex.practicum.shareIt.maker.OnCreate;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemControllerImpl {
    private final ItemService service;

    @PostMapping
    public ItemDto postItem(@RequestHeader("X-Sharer-User-Id") long userId,
                         @Validated({OnCreate.class}) @RequestBody ItemDto itemDto) {
        return service.postItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable long itemId,
                           @RequestBody ItemDto itemDto) {
        return service.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable long itemId) {
        return service.getItem(itemId);
    }

    @GetMapping
    public List<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.getUserItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> itemSearch(@RequestParam() String text) {
        return service.itemSearch(text);
    }

    @DeleteMapping
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId, long itemId) {
        service.deleteItem(userId, itemId);
    }
}
