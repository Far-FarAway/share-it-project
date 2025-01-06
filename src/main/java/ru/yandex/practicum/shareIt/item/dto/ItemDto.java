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
    private Boolean available;
    private List<ReviewDto> reviews;
    private Integer bookCount;
}
