package ru.yandex.practicum.shareIt.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.maker.OnCreate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @Positive
    Long id;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    String name;
    @Email(groups = {Builder.Default.class, OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    String email;
}
