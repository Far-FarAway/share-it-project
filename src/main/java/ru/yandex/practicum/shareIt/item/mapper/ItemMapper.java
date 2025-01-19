package ru.yandex.practicum.shareIt.item.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.booking.repository.BookingRepository;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class ItemMapper {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

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

    public ItemDto makeDto(Item item) {
        long id = item.getId();
        boolean isExists = bookingRepository.existsByItemId(id);

        return ItemDto.builder()
                .id(id)
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .bookCount(item.getBookCount())
                .nextStart(isExists ? bookingRepository.getNearliestFutureBooking(id).getStart().toString() : null)
                .pastEnd(isExists ? bookingRepository.getNearliestPastBooking(id).getEnd().toString() : null)
                .build();
    }
}
