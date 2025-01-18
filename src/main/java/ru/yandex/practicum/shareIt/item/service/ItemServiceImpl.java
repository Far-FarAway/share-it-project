package ru.yandex.practicum.shareIt.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.exception.ConditionsNotMatchException;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
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
        userRepository.existsById(userId);
        Item item = itemRepository.save(ItemMapper.makePOJO(userId, itemDto));
        return ItemMapper.makeDto(item);
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        if (itemRepository.checkItemOwner(itemId) == userId) {
            Item item = ItemMapper.makePOJO(userId, itemDto);
            item.setId(itemId);
            return ItemMapper.makeDto(itemRepository.save(item));
        } else {
            throw new ConditionsNotMatchException("Только владелец может изменять данные предмета");
        }
    }

    @Override
    public ItemDto getItem(long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с id '" + itemId + "' не найден"));
        if (item != null) {
            return ItemMapper.makeDto(item);
        } else {
            throw new NotFoundException("Не найден предмет с id: " + itemId);
        }
    }

    @Override
    public List<ItemDto> getUserItems(long userId) {
        userRepository.existsById(userId);
        return itemRepository.findByUserId(userId).stream()
                .map(ItemMapper::makeDto)
                .toList();
    }

    @Override
    public List<ItemDto> itemSearch(String text) {
        return itemRepository.findByNameContainingOrDescriptionContaining(text).stream()
                .map(ItemMapper::makeDto)
                .toList();
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        if (itemRepository.checkItemOwner(itemId) == userId) {
            itemRepository.deleteById(itemId);
        } else {
            throw new ConditionsNotMatchException("Только владелец может удалить предмет");
        }
    }
}
