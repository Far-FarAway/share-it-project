package ru.yandex.practicum.shareIt.user.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceUnitTest {
    @Mock
    UserRepository repository;
    UserService service;

    @BeforeEach
    void before() {
        service = new UserServiceImpl(repository);
    }

    @Test
    void testDeleteUser() {
        service.deleteUser(333);

        Mockito.verify(repository, Mockito.times(1))
                .deleteById(333L);
    }
}