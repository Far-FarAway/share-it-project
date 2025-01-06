package ru.yandex.practicum.shareIt.item.mapper;

import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.review.Review;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoMapper {
    public static ItemDto map(Item item) {
        List<Review> reviewList =  item.getReviews();
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
