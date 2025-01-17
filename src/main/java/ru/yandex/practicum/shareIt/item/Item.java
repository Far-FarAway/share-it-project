package ru.yandex.practicum.shareIt.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private Long id;
    private Long owner;
    private String name;
    private String description;
    private Boolean available;
    private Integer bookCount;
}
