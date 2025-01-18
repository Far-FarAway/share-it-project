package ru.yandex.practicum.shareIt.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.exception.SameEmailException;
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
        if(repository.existsByEmail(userDto.getEmail())) {
            throw new SameEmailException("Почта '" + userDto.getEmail() + "' занята");
        }

        User user = UserMapper.makePOJO(userDto);
        return UserMapper.makeDto(repository.save(user));
    }

    @Override
    public UserDto getUser(long id) {
        return UserMapper.makeDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("пользователь с id '" + id + "' не найден")));
    }

    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        if(repository.existsByEmail(userDto.getEmail())) {
            throw new SameEmailException("Почта '" + userDto.getEmail() + "' занята");
        }

        User user = UserMapper.makePOJO(userDto);
        user.setId(id);
        return UserMapper.makeDto(repository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}
