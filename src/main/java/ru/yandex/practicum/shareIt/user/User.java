package ru.yandex.practicum.shareIt.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.maker.OnCreate;

@Data
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
}
