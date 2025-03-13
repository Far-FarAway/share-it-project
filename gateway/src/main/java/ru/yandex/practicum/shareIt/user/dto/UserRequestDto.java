package ru.yandex.practicum.shareIt.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.marker.OnCreate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    Long id;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    String name;
    @Email(groups = {OnCreate.class, OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    String email;

}
