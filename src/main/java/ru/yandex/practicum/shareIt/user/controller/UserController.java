package ru.yandex.practicum.shareIt.user.controller;

import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

public interface UserController {
    User saveUser(User user);

    UserDto getUser(long id);

    User updateUser(long id, User user);

    void deleteUser(long id);
}
