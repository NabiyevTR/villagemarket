package ru.ntr.villagemarket.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.user.UserDto;
import ru.ntr.villagemarket.model.service.UserService;

@RestController
@RequestMapping("/api/profile")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    //TODO Show current user
    public UserDto showUser() {
        return userService.showCurrentUser();
    }

    @PatchMapping("/edit")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }
}

