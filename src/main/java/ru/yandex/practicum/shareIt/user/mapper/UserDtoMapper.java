package ru.yandex.practicum.shareIt.user.mapper;

import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

public class UserDtoMapper {
    public static UserDto map(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
