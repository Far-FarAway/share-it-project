package ru.yandex.practicum.shareIt.review;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

@Data
@Builder
public class Review {
    private long id;
    private String reviewerName;
    private String description;a
}
