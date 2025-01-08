package ru.yandex.practicum.shareIt.item.mapper;

import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.review.Review;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    public static Item makePOJO(long userId, ItemDto itemDto) {
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

    public static ItemDto makeDto(Item item) {
        List<Review> reviewList = item.getReviews();
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        if (reviewList != null) {
            reviewDtoList = reviewList.stream()
                    .map(review -> {
                        return ReviewDto.builder()
                                .reviewerName(review.getReviewerName())
                                .description(review.getDescription())
                                .build();
                    })
                    .toList();
        }

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .reviews(reviewDtoList)
                .bookCount(item.getBookCount())
                .build();
    }
}
