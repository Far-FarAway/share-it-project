package ru.yandex.practicum.shareIt.item.repository;

import ru.yandex.practicum.shareIt.item.Item;

import java.util.List;

public interface ItemRepository {
    Item postItem(Item item);

    Item updateItem(long itemId, Item item);

    Item getItem(long itemId);

    List<Item> getUserItems(long userID);

    List<Item> itemSearch(String text);

    void deleteItem(long itemId);

    boolean checkOwner(long userId, long itemId);
}
