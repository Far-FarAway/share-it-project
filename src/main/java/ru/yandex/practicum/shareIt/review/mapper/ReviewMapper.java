package ru.yandex.practicum.shareIt.review.mapper;

import ru.yandex.practicum.shareIt.review.Review;

public class ReviewMapper {
    public static Review map(Review review) {
        return Review.builder()
                .id(review.getId())
                .reviewerName(review.getReviewerName())
                .description(review.getDescription())
                .build();
    }
}
