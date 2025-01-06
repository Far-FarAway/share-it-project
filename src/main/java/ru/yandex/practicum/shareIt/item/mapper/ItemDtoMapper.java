package ru.yandex.practicum.shareIt.item.mapper;

import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

import java.util.List;

public class ItemDtoMapper {
    public static ItemDto map(Item item) {
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
