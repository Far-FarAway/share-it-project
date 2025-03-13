package ru.yandex.practicum.shareIt.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.marker.OnCreate;
import ru.yandex.practicum.shareIt.marker.OnUpdate;
import ru.yandex.practicum.shareIt.user.dto.UserRequestDto;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserClient client;

    @PostMapping
    public ResponseEntity<UserRequestDto> saveUser(@Validated(OnCreate.class) @RequestBody UserRequestDto userDto) {
        log.info("Post user {}", userDto);
        return client.saveUser(userDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRequestDto> getUser(@PathVariable long userId) {
        log.info("Get user with id {}", userId);
        return client.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserRequestDto> updateUser(@PathVariable long userId, @Validated(OnUpdate.class) @RequestBody UserRequestDto userDto) {
        log.info("Update user {}, userId={}", userDto, userId);
        return client.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserRequestDto> deleteUser(@PathVariable long userId) {
        log.info("Delete user with id {}", userId);
        return client.deleteUser(userId);
    }
}
