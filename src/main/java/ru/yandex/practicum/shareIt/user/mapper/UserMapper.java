package ru.yandex.practicum.shareIt.user.mapper;

import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

public class UserMapper {
    public static User map(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
