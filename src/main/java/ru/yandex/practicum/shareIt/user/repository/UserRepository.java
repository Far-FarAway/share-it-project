package ru.yandex.practicum.shareIt.user.repository;

import ru.yandex.practicum.shareIt.user.DTO.UserDTO;
import ru.yandex.practicum.shareIt.user.User;

public interface UserRepository {
    User saveUser(User user);

    UserDTO getUser(long id);

    User updateUser(long id, User user);

    void deleteUser(long id);

    void checkSameEmail(String email);

    void isUserExists(Long userId);
}
