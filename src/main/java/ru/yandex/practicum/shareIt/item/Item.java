package ru.yandex.practicum.shareIt.item;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.review.Review;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Item {
    private Long id;
    private Long owner;
    private String name;
    private String description;
    private Boolean available;
    private List<Review> reviews = new ArrayList<>();
    private Integer bookCount;
}
