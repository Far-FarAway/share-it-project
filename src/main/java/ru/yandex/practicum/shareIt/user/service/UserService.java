package ru.yandex.practicum.shareIt.user.service;

import ru.yandex.practicum.shareIt.user.dto.UserDto;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    UserDto getUser(long id);

    UserDto updateUser(long id, UserDto userDto);

    void deleteUser(long id);
}
