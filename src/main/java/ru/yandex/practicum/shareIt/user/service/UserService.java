package ru.yandex.practicum.shareIt.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

@Transactional(readOnly = true)
public interface UserService {
    @Transactional
    UserDto saveUser(UserDto userDto);

    UserDto getUser(long id);

    @Transactional
    UserDto updateUser(long id, UserDto userDto);

    @Transactional
    void deleteUser(long id);
}
