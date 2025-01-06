package ru.yandex.practicum.shareIt.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.maker.OnCreate;
import ru.yandex.practicum.shareIt.review.dto.ReviewDto;

import java.util.List;

@Data
@Builder
public class ItemDto {
    @Positive
    private Long id;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String name;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String description;
    @NotNull(groups = {OnCreate.class})
    private Boolean available;
    private List<ReviewDto> reviews;
    private Integer bookCount;
}
