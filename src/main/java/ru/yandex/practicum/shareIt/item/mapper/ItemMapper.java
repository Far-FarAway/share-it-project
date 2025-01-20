package ru.yandex.practicum.shareIt.item.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.repository.BookingRepository;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.CommentDto;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.repository.CommentRepository;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemMapper {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public Item makePOJO(long userId, ItemDto itemDto) {
        return Item.builder()
                .user(userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Пользователь с id '" + userId + "' не найден")))
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .bookCount(itemDto.getBookCount())
                .build();
    }

    /*Параметр initDate только для последнего теста комментариев в постмане
    В тз сказано, чтобы даты показывались при эндпоинте GET items
    Я подумал, что можно сделать dto без дат, чтобы использовать в остальных методах, но в
    тесте идет проверка на наличие полей с датами*/
    public ItemDto makeDto(Item item, boolean initDate) {
        long id = item.getId();
        Booking latestBooking = null;
        Booking nextBooking = null;

        if (bookingRepository.existsByItemId(id) && initDate) {
            latestBooking = bookingRepository.getNearliestPastBooking(id);
            nextBooking = bookingRepository.getNearliestFutureBooking(id);
        }

        List<CommentDto> comments = commentRepository.findByItemId(id).stream()
                .map(comment -> commentMapper.makeDto(id, comment))
                .toList();

        return ItemDto.builder()
                .id(id)
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
