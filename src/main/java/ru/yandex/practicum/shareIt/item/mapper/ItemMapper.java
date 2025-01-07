package ru.yandex.practicum.shareIt.item.mapper;

import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.review.Review;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    public static Item map(long userId, ItemDto itemDto) {
        List<ReviewDto> reviewDtoList = itemDto.getReviews();
        List<Review> reviewList = new ArrayList<>();
        if (reviewDtoList != null) {
            reviewList = reviewDtoList.stream()
                    .map(reviewDto -> {
                        return Review.builder()
                                .reviewerName(reviewDto.getReviewerName())
                                .description(reviewDto.getDescription())
                                .build();
                    })
                    .toList();
        }

        return Item.builder()
                .owner(userId)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .reviews(reviewList)
                .bookCount(itemDto.getBookCount())
                .build();
    }
}
