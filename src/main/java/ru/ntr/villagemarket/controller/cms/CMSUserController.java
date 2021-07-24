package ru.ntr.villagemarket.controller.cms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.UserDto;
import ru.ntr.villagemarket.model.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/cms/user")
@Slf4j
@RequiredArgsConstructor
@Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
public class CMSUserController {

    private final UserService userService;

    @GetMapping()
    public List<UserDto> showUsers() {
        return userService.findAll();
    }


    @GetMapping("/{id}")
    public UserDto showUser(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PostMapping("")
    public int create(@RequestBody UserDto userDto) {
        userService.save(userDto);
        return HttpStatus.OK.value();
    }

    @PatchMapping(value = "/{id}/edit")
    public int updateProduct(@RequestBody UserDto userDto, @PathVariable("id") int id) {
        userService.save(userDto);
        return HttpStatus.OK.value();
    }

    @DeleteMapping("/{id}")
    public int deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return HttpStatus.OK.value();
    }
}
