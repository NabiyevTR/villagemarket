package ru.ntr.villagemarket.controller.cms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.user.CustomerBasicDto;
import ru.ntr.villagemarket.model.dto.user.CustomerFullDto;
import ru.ntr.villagemarket.model.service.UserService;
import ru.ntr.villagemarket.responses.Response;

import java.util.List;

@RestController
@RequestMapping("/api/cms/customer")
@Slf4j
@RequiredArgsConstructor
@Secured({"ROLE_SUPERADMIN", "ROLE_MANAGER"})
public class CMSCustomerController {

    private final UserService userService;

    @GetMapping()
    public List<CustomerBasicDto> showCustomers() {
        return userService.findAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerFullDto showCustomer(@PathVariable("id") int id) {
        return userService.findCustomerById(id);
    }
}

