package ru.yandex.practicum.shareIt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.shareIt.user.User;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsById(Long userId);
}
