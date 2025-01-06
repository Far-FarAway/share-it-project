package ru.yandex.practicum.shareIt.user.controller;

import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDTO;

public interface UserController {
    User saveUser(User user);

    UserDTO getUser(long id);

    User updateUser(long id, User user);

    void deleteUser(long id);
}
