package ru.yandex.practicum.shareIt.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    private long id;
    private String reviewerName;
    private String description;
}
