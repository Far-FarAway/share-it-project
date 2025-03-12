package ru.yandex.practicum.shareIt.request.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ru.yandex.practicum.shareIt.ShareItServer;
import ru.yandex.practicum.shareIt.request.Request;
import ru.yandex.practicum.shareIt.request.dto.RequestDto;
import ru.yandex.practicum.shareIt.request.repository.RequestRepository;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(classes = ShareItServer.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
class RequestServiceIntegrationTest {
    final UserRepository userRepository;
    final RequestRepository requestRepository;
    final RequestService service;

    User user;
    User user2;
    Request request;
    Request request2;
    Request request3;

    @BeforeEach
    void before() {
        user = User.builder()
                .name("Shrek")
                .email("shrekIsLove@gmail.com")
                .build();

        user2 = User.builder()
                .name("Donkey")
                .email("shrekIsLife@gmail.com")
                .build();

        request = Request.builder()
                .user(user)
                .description("I wanna some shrexy stuff")
                .build();

        request2 = Request.builder()
                .user(user)
                .description("Swampy swampy swampy swamp")
                .build();

        request3 = Request.builder()
                .user(user2)
                .description("CUPCAKES")
                .build();
    }

    @Test
    void testGetUserRequests() {
        userRepository.save(user);
        userRepository.save(user2);
        requestRepository.save(request);
        requestRepository.save(request2);
        requestRepository.save(request3);

        List<RequestDto> resp = service.getUserRequests(user.getId());

        assertThat(resp.size(), is(2));
        assertThat(resp.getLast().getDescription(), is(request2.getDescription()));
    }

    @Test
    void testGetAllRequests() {
        userRepository.save(user);
        userRepository.save(user2);
        requestRepository.save(request);
        requestRepository.save(request2);
        requestRepository.save(request3);

        List<RequestDto> resp = service.getAllRequests(user.getId());

        assertThat(resp.size(), is(1));
        assertThat(resp.getFirst().getDescription(), is(request3.getDescription()));
    }

    @Test
    void testGetRequestById() {
        userRepository.save(user);
        requestRepository.save(request);

        RequestDto resp = service.getRequestById(request.getId());

        assertThat(resp.getDescription(), is(request.getDescription()));
    }
}