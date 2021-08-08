package ru.ntr.villagemarket.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.user.UserDto;
import ru.ntr.villagemarket.model.dto.user.UserProfileDto;
import ru.ntr.villagemarket.model.mapper.UserProfileMapper;
import ru.ntr.villagemarket.model.service.UserService;

@RestController
@RequestMapping("/api/profile")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserProfileDto showUser() {
        return userService.getCurrentUserProfile();
    }

    @PatchMapping
    public void updateUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }
}

