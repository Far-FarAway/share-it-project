package ru.yandex.practicum.shareIt.item.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Item;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Comparator<Long> comparator = Comparator.comparing(ob -> ob);
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item postItem(Item item) {
        long id = items.values().stream()
                .map(Item::getId)
                .max(comparator)
                .orElse((long) 0) + 1;

        item.setId(id);
        items.put(id, item);

        return item;
    }

    @Override
    public Item updateItem(long itemId, Item item) {
        Item oldItem = items.get(itemId);
        Item updatedItem = Item.builder()
                .id(itemId)
                .owner(oldItem.getOwner())
                .name(item.getName() == null || item.getName().isBlank() ? oldItem.getName() : item.getName())
                .description(item.getDescription() == null || item.getDescription().isBlank() ?
                        oldItem.getDescription() : item.getDescription())
                .available(item.getAvailable() == null ? oldItem.getAvailable() : item.getAvailable())
                .reviews(item.getReviews() == null ? oldItem.getReviews() : item.getReviews())
                .bookCount(item.getBookCount() == null ? oldItem.getBookCount() : item.getBookCount())
                .build();

        items.put(itemId, updatedItem);
        return updatedItem;
    }

    @Override
    public Item getItem(long itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> getUserItems(long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner() == userId)
                .toList();
    }

    @Override
    public List<Item> itemSearch(String text) {
        return items.values().stream()
                .filter(item -> !(text.isBlank()) && item.getAvailable())
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .toList();
    }

    @Override
    public void deleteItem(long itemId) {
        items.remove(itemId);
    }

    @Override
    public boolean checkOwner(long userId, long itemId) {
        if (items.containsKey(itemId)) {
            return items.get(itemId).getOwner() == userId;
        } else {
            throw new NotFoundException("Не найден предмет с id: " + itemId);
        }
    }
}
