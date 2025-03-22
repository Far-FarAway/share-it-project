package ru.yandex.practicum.shareIt.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.exception.SameEmailException;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDto;
import ru.yandex.practicum.shareIt.user.mapper.UserMapper;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository repository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (repository.existsByEmail(userDto.getEmail())) {
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
        if (repository.existsByEmail(userDto.getEmail())) {
            throw new SameEmailException("Почта '" + userDto.getEmail() + "' занята");
        }

        User user = UserMapper.makePOJO(userDto);
        user.setId(id);

        User oldUser = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("пользователь с id '" + id + "' не найден"));

        user.setEmail(user.getEmail() == null ? oldUser.getEmail() : user.getEmail());
        user.setName(user.getName() == null ? oldUser.getName() : user.getName());

        return UserMapper.makeDto(repository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}
