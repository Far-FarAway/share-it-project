package ru.yandex.practicum.shareIt.review.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {
    private String reviewerName;
    private String description;
}
