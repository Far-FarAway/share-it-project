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

    @Override
    public UserDto saveUser(UserDto userDto) {
        repository.checkSameEmail(userDto.getEmail());
        User user = makePOJO(userDto);
        return makeDto(repository.saveUser(user));
    }

    @Override
    public UserDto getUser(long id) {
        return makeDto(repository.getUser(id));
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        repository.checkSameEmail(userDto.getEmail());
        User user = makePOJO(userDto);
        return makeDto(repository.updateUser(id, user));
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteUser(id);
    }

    private User makePOJO(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    private UserDto makeDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
