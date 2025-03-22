package ru.yandex.practicum.shareIt.user.mapper;

import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

public class UserMapper {
    public static User makePOJO(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static UserDto makeDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
