package ru.yandex.practicum.shareIt.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.user.dto.UserDto;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public User saveUser(User user) {
        repository.checkSameEmail(user.getEmail());
        return repository.saveUser(user);
    }

    public UserDto getUser(long id) {
        return repository.getUser(id);
    }

    public User updateUser(long id, User user) {
        repository.checkSameEmail(user.getEmail());
        return repository.updateUser(id, user);
    }

    public void deleteUser(long id) {
        repository.deleteUser(id);
    }
}
