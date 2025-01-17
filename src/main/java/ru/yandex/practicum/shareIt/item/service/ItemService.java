package ru.yandex.practicum.shareIt.item.service;

import ru.yandex.practicum.shareIt.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto postItem(long userId, ItemDto item);

    ItemDto updateItem(long userId, long itemId, ItemDto item);

    ItemDto getItem(long itemId);

    List<ItemDto> getUserItems(long userID);

    List<ItemDto> itemSearch(String text);

    void deleteItem(long userId, long itemId);
}
