package ru.yandex.practicum.shareIt.user.service;

import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

public interface UserService {
    User saveUser(User user);

    User getUser(long id);

    User updateUser(long id, User user);

    void deleteUser(long id);
}
