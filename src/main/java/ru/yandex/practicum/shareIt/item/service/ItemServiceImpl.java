package ru.yandex.practicum.shareIt.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.exception.ConditionsNotMatchException;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.review.Review;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto postItem(long userId, ItemDto itemDto) {

        Item item = itemRepository.postItem(makePOJO(userId, itemDto));
        return makeDto(itemRepository.postItem(item));
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        if (itemRepository.checkOwner(userId, itemId)) {
            Item item = makePOJO(userId, itemDto);
            return makeDto(itemRepository.updateItem(itemId, item));
        } else {
            throw new ConditionsNotMatchException("Только владелец может изменять данные предмета");
        }
    }

    @Override
    public ItemDto getItem(long itemId) {
        return makeDto(itemRepository.getItem(itemId));
    }

    @Override
    public List<ItemDto> getUserItems(long userId) {
        return itemRepository.getUserItems(userId).stream()
                .map(this::makeDto)
                .toList();
    }

    @Override
    public List<ItemDto> itemSearch(String text) {
        return itemRepository.itemSearch(text).stream()
                .map(this::makeDto)
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

    private Item makePOJO(long userId, ItemDto itemDto) {
        List<Review> reviewList =  itemDto.getReviews().stream()
                .map(reviewDto -> {
                    return Review.builder()
                            .reviewerName(reviewDto.getReviewerName())
                            .description(reviewDto.getDescription())
                            .build();
                })
                .toList();

        return Item.builder()
                .id(userId)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .reviews(reviewList)
                .bookCount(itemDto.getBookCount())
                .build();
    }

    private ItemDto makeDto(Item item) {
        List<ReviewDto> reviewDtoList =  item.getReviews().stream()
                .map(review -> {
                    return ReviewDto.builder()
                            .reviewerName(review.getReviewerName())
                            .description(review.getDescription())
                            .build();
                })
                .toList();

        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .reviews(reviewDtoList)
                .bookCount(item.getBookCount())
                .build();
    }
}
