package ru.yandex.practicum.shareIt.item.mapper;

import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;

public class ItemMapper {
    public static Item makePOJO(long userId, ItemDto itemDto) {
        return Item.builder()
                .owner(userId)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .bookCount(itemDto.getBookCount())
                .build();
    }

    public static ItemDto makeDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .bookCount(item.getBookCount())
                .build();
    }
}
