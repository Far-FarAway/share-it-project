package ru.yandex.practicum.shareIt.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.maker.OnCreate;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;
import ru.yandex.practicum.shareIt.review.Review;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Item {
    private Long id;
    private Long owner;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String name;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String description;
    @NotNull(groups = {OnCreate.class})
    private Boolean available;
    private List<Review> reviews = new ArrayList<>();
    private Integer bookCount;

    public ItemDto makeDTO() {
        List<ReviewDto> reviewDtoList = reviews.stream()
                .map(Review::getDTO)
                .toList();

        return ItemDto.builder()
                .name(name)
                .description(description)
                .available(available)
                .review(reviewDtoList)
                .bookCount(bookCount)
                .build();
    }
}
