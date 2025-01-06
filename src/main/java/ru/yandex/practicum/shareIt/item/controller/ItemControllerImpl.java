package ru.yandex.practicum.shareIt.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.item.Item;
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
    public Item postItem(@RequestHeader("X-Sharer-User-Id") long userId,
                         @Validated({OnCreate.class}) @RequestBody Item item) {
        return service.postItem(userId, item);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable long itemId,
                           @RequestBody Item item) {
        return service.updateItem(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable long itemId) {
        return service.getItem(itemId);
    }

    @GetMapping
    public List<Item> getUserItems(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.getUserItems(userId);
    }

    @GetMapping("/search")
    public List<Item> itemSearch(@RequestParam() String text) {
        return service.itemSearch(text);
    }

    @DeleteMapping
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId, long itemId) {
        service.deleteItem(userId, itemId);
    }
}
