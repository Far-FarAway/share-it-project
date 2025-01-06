package ru.yandex.practicum.shareIt.review.mapper;

import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

public class ReviewDtoMapper {
    public static ReviewDto map(ReviewDto reviewDto) {
        return ReviewDto.builder()
                .reviewerName(reviewDto.getReviewerName())
                .description(reviewDto.getDescription())
                .build();
    }
}
