package ru.yandex.practicum.shareIt.item.service;

import ru.yandex.practicum.shareIt.item.Item;

import java.util.List;

public interface ItemService {
    Item postItem(long userId, Item item);

    Item updateItem(long userId, long itemId, Item item);

    Item getItem(long itemId);

    List<Item> getItems(long userID);

    List<Item> itemSearch(String text);

    void deleteItem(long userId, long itemId);
}
