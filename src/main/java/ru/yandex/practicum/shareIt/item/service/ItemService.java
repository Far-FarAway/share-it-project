package ru.yandex.practicum.shareIt.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.shareIt.item.dto.CommentDto;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    @Transactional
    ItemDto postItem(long userId, ItemDto item);

    @Transactional
    ItemDto updateItem(long userId, long itemId, ItemDto item);

    ItemDto getItem(long itemId);

    List<ItemDto> getUserItems(long userID);

    List<ItemDto> itemSearch(String text);

    @Transactional
    void deleteItem(long userId, long itemId);

    @Transactional
    CommentDto addComment(long userId, long itemId, CommentDto commentDto);
}
