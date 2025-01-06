package ru.yandex.practicum.shareIt.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.exception.ConditionsNotMatchException;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.mapper.ItemDtoMapper;
import ru.yandex.practicum.shareIt.item.mapper.ItemMapper;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto postItem(long userId, ItemDto itemDto) {
        userRepository.isUserExists(userId);
        Item item = itemRepository.postItem(ItemMapper.map(userId, itemDto));
        return ItemDtoMapper.map(itemRepository.postItem(item));
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        if (itemRepository.checkOwner(userId, itemId)) {
            Item item = ItemMapper.map(userId, itemDto);
            return ItemDtoMapper.map(itemRepository.updateItem(itemId, item));
        } else {
            throw new ConditionsNotMatchException("Только владелец может изменять данные предмета");
        }
    }

    @Override
    public ItemDto getItem(long itemId) {
        Item item = itemRepository.getItem(itemId);
        if (item != null) {
            return ItemDtoMapper.map(item);
        } else {
            throw new NotFoundException("Не найден предмет с id: " + itemId);
        }
    }

    @Override
    public List<ItemDto> getUserItems(long userId) {
        userRepository.isUserExists(userId);
        return itemRepository.getUserItems(userId).stream()
                .map(ItemDtoMapper::map)
                .toList();
    }

    @Override
    public List<ItemDto> itemSearch(String text) {
        return itemRepository.itemSearch(text).stream()
                .map(ItemDtoMapper::map)
                .toList();
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        if (itemRepository.checkOwner(userId, itemId)) {
            itemRepository.deleteItem(itemId);
        } else {
            throw new ConditionsNotMatchException("Только владелец может удалить предмет");
        }
    }
}
