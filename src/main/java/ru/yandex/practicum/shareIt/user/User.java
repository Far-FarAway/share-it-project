package ru.yandex.practicum.shareIt.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.maker.OnCreate;
import ru.yandex.practicum.shareIt.user.DTO.UserDTO;

@Data
@Builder
public class User {
    @Positive
    private Long id;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String name;
    @Email(groups = {Builder.Default.class, OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String email;

    public UserDTO makeDTO() {
        return UserDTO.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
    }
}
