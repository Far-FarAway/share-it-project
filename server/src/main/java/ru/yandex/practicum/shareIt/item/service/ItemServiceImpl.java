package ru.yandex.practicum.shareIt.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.repository.BookingRepository;
import ru.yandex.practicum.shareIt.exception.BadRequestException;
import ru.yandex.practicum.shareIt.exception.ConditionsNotMatchException;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Comment;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.CommentDto;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.mapper.CommentMapper;
import ru.yandex.practicum.shareIt.item.mapper.ItemMapper;
import ru.yandex.practicum.shareIt.item.repository.CommentRepository;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.request.Request;
import ru.yandex.practicum.shareIt.request.repository.RequestRepository;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;
    UserRepository userRepository;
    BookingRepository bookingRepository;
    CommentRepository commentRepository;
    RequestRepository requestRepository;
    CommentMapper commentMapper;
    ItemMapper itemMapper;

    @Override
    public ItemDto postItem(long userId, ItemDto itemDto) {
        userRepository.existsById(userId);
        Item item = itemRepository.save(prepareAndMakeItemPOJO(userId, itemDto));

        if (itemDto.getRequestId() != null) {
            Request request = requestRepository.findById(itemDto.getRequestId()).orElseThrow(() ->
                    new NotFoundException("Запрос с таким id: " + itemDto.getRequestId() + " не найден"));

            request.getItemIds().add(item.getId());
            requestRepository.save(request);
        }

        return prepareAndMakeItemDto(item, false);
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        if (itemRepository.checkItemOwner(itemId) == userId) {
            Item item = prepareAndMakeItemPOJO(userId, itemDto);
            item.setId(itemId);

            Item oldItem = itemRepository.findById(itemId)
                    .orElseThrow(() -> new NotFoundException("Предмет с id '" + itemId + "' не найден"));
            item.setName(item.getName() == null ? oldItem.getName() : item.getName());
            item.setUser(item.getUser() == null ? oldItem.getUser() : item.getUser());
            item.setAvailable(item.getAvailable() == null ? oldItem.getAvailable() : item.getAvailable());
            item.setDescription(item.getDescription() == null ? oldItem.getDescription() : item.getDescription());

            return prepareAndMakeItemDto(itemRepository.save(item), false);
        } else {
            throw new ConditionsNotMatchException("Только владелец может изменять данные предмета");
        }
    }

    @Override
    public ItemDto getItem(long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с id '" + itemId + "' не найден"));

        return prepareAndMakeItemDto(item, false);
    }

    @Override
    public List<ItemDto> getUserItems(long userId) {
        userRepository.existsById(userId);
        return itemRepository.findByUserId(userId).stream()
                .map(item -> prepareAndMakeItemDto(item, true))
                .toList();
    }

    @Override
    public List<ItemDto> itemSearch(String text) {
        return itemRepository.findByNameContainingOrDescriptionContaining(text.toLowerCase()).stream()
                .map(item -> prepareAndMakeItemDto(item, false))
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

    @Override
    public CommentDto addComment(long userId, long itemId, CommentDto commentDto) {
        if (!bookingRepository.existsValidBookingForAddComment(userId)) {
            throw new BadRequestException("Чтобы оставить отзыв на предмет," +
                    " нужно воспользоваться им");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с id '" + itemId +
                        "' не найден"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id '" + userId + "' не найден"));

        commentDto.setId(itemId);
        commentDto.setAuthorName(user.getName());
        Comment comment = commentMapper.makePOJO(item, commentDto);

        return commentMapper.makeDto(commentRepository.save(comment));
    }

    @Override
    public ItemDto prepareAndMakeItemDto(Item item, boolean initDate) {
        long id = item.getId();
        Booking latestBooking = null;
        Booking nextBooking = null;

        if (bookingRepository.existsByItemId(id) && initDate) {
            latestBooking = bookingRepository.getNearliestPastBooking(id);
            nextBooking = bookingRepository.getNearliestFutureBooking(id);
        }

        List<CommentDto> comments = commentRepository.findByItemId(id).stream()
                .map(commentMapper::makeDto)
                .toList();

        return itemMapper.makeDto(item, comments, nextBooking, latestBooking);
    }

    @Override
    public Item prepareAndMakeItemPOJO(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id '" + userId + "' не найден"));

        return itemMapper.makePOJO(user, itemDto);
    }
}
