package ru.yandex.practicum.shareIt.user.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.exception.SameEmailException;
import ru.yandex.practicum.shareIt.user.dto.UserDTO;
import ru.yandex.practicum.shareIt.user.User;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Comparator<Long> comparator = Comparator.comparing(ob -> ob);
    private final Map<Long, User> users = new HashMap<>();

    public User saveUser(User user) {
        long id = users.values().stream()
                .map(User::getId)
                .max(comparator)
                .orElse((long) 0) + 1;

        user.setId(id);
        users.put(id, user);

        return user;
    }

    public UserDTO getUser(long id) {
        return users.get(id).makeDTO();
    }

    public User updateUser(long id, User user) {
        User oldUser = users.get(id);
        oldUser.setName(user.getName() == null ? oldUser.getName() : user.getName());
        oldUser.setEmail(user.getEmail() == null ? oldUser.getEmail() : user.getEmail());
        users.put(id, oldUser);
        return oldUser;
    }

    public void deleteUser(long id) {
        users.remove(id);
    }

    public void checkSameEmail(String email) {
        if (email != null) {
            Optional<User> sameEmail = users.values().stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findAny();

            if (sameEmail.isPresent()) {
                throw new SameEmailException("Такая почта уже существует");
            }
        }
    }

    public void isUserExists(Long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("Не найден пользователь с id: " + userId);
        }
    }
}
