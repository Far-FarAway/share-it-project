package ru.yandex.practicum.shareIt.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.user.dto.UserDto;
import ru.yandex.practicum.shareIt.user.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl {
    private final UserService service;

    @PostMapping
    public UserDto saveUser( @RequestBody UserDto userDto) {
        return service.saveUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId) {
        return service.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable long userId, @RequestBody UserDto userDto) {
        return service.updateUser(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        service.deleteUser(userId);
    }
}
