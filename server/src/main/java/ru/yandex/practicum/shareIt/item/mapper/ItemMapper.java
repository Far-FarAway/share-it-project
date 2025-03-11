package ru.yandex.practicum.shareIt.item.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.CommentDto;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemMapper {
    private final UserRepository userRepository;

    public Item makePOJO(User user, ItemDto itemDto) {
        return Item.builder()
                .user(user)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .bookCount(itemDto.getBookCount())
                .build();
    }

    public ItemDto makeDto(Item item, List<CommentDto> comments,
                           Booking nextBooking, Booking latestBooking) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .bookCount(item.getBookCount())
                .comments(comments)
                .nextBooking(nextBooking != null ? nextBooking.getStart().toString() : null)
                .lastBooking(latestBooking != null ? latestBooking.getEnd().toString() : null)
                .build();
    }
}
