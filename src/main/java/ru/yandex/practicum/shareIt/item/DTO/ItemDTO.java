package ru.yandex.practicum.shareIt.item.DTO;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.review.DTO.ReviewDTO;

import java.util.List;

@Data
@Builder
public class ItemDTO {
    private String name;
    private String description;
    private boolean available;
    private List<ReviewDTO> review;
    private Integer bookCount;
}
