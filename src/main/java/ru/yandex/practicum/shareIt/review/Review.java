package ru.yandex.practicum.shareIt.review;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.review.DTO.ReviewDTO;

@Data
@Builder
public class Review {
    private long id;
    private String reviewerName;
    private String description;

    public ReviewDTO getDTO() {
        return ReviewDTO.builder()
                .reviewerName(reviewerName)
                .description(description)
                .build();
    }
}
