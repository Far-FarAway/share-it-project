package ru.yandex.practicum.shareIt.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.exception.ConditionsNotMatchException;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Item postItem(long userId, Item item) {
        userRepository.isUserExists(userId);
        return itemRepository.postItem(userId, item);
    }

    public Item updateItem(long userId, long itemId, Item item) {
        if (itemRepository.checkOwner(userId, itemId)) {
            return itemRepository.updateItem(itemId, item);
        } else {
            throw new ConditionsNotMatchException("Только владелец может изменять данные предмета");
        }
    }

    public Item getItem(long itemId) {
        return itemRepository.getItem(itemId);
    }

    public List<Item> getItems(long userId) {
        return itemRepository.getItems(userId);
    }

    public List<Item> itemSearch(String text) {
        return itemRepository.itemSearch(text);
    }

    public void deleteItem(long userId, long itemId) {
        if (itemRepository.checkOwner(userId, itemId)) {
            itemRepository.deleteItem(itemId);
        } else {
            throw new ConditionsNotMatchException("Только владелец может удалить предмет");
        }
    }
}
