package ru.yandex.practicum.shareIt.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.maker.OnCreate;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.dto.UserDTO;
import ru.yandex.practicum.shareIt.user.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserControllerImpl implements UserController {
    private final UserService service;

    @PostMapping
    public User saveUser(@Validated({OnCreate.class}) @RequestBody User user) {
        return service.saveUser(user);
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable long userId) {
        return service.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody User user) {
        return service.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        service.deleteUser(userId);
    }
}
