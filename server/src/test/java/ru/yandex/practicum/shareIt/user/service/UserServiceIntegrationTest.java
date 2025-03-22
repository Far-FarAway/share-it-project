package ru.yandex.practicum.shareIt.user.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.shareIt.ShareItServer;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDto;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ShareItServer.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceIntegrationTest {
    final UserService service;
    final UserRepository repository;

    User user;

    @BeforeEach
    void before() {
        user = User.builder()
                .name("Shrek")
                .email("shrekIsLove@gmail.com")
                .build();
    }

    @Test
    void testGetUser() {
        repository.save(user);

        UserDto resp = service.getUser(user.getId());

        assertThat(resp.getEmail(), is(user.getEmail()));
        assertThat(resp.getName(), is(user.getName()));
    }

    @Test
    void testUpdateUser() {
        repository.save(user);

        UserDto dto = UserDto.builder().name("TREBUSHET").build();

        UserDto resp = service.updateUser(user.getId(), dto);

        assertThat(resp.getName(), is(dto.getName()));
        assertThat(resp.getEmail(), is(user.getEmail()));
    }
}