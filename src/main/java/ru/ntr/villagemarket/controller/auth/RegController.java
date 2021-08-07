package ru.ntr.villagemarket.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ntr.villagemarket.model.dto.user.NewUserDto;
import ru.ntr.villagemarket.model.service.UserService;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegController {

    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody NewUserDto newUserDto) {
        userService.regUser(newUserDto);
    }
}
