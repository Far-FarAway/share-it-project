package ru.yandex.practicum.shareIt.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.user.dto.UserDto;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.mapper.UserMapper;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        repository.checkSameEmail(userDto.getEmail());
        User user = UserMapper.makePOJO(userDto);
        return UserMapper.makeDto(repository.saveUser(user));
    }

    @Override
    public UserDto getUser(long id) {
        return UserMapper.makeDto(repository.getUser(id));
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        repository.checkSameEmail(userDto.getEmail());
        User user = UserMapper.makePOJO(userDto);
        return UserMapper.makeDto(repository.updateUser(id, user));
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteUser(id);
    }
}
