package ru.yandex.practicum.shareIt.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

import java.util.List;

@Data
@Builder
public class ItemDto {
    private String name;
    private String description;
    private boolean available;
    private List<ReviewDto> review;
    private Integer bookCount;
}
