package ru.ntr.villagemarket.controller.cms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ntr.villagemarket.controller.AbstractControllerIntegrationTest;
import ru.ntr.villagemarket.model.entity.Role;
import ru.ntr.villagemarket.model.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CMSCustomerControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Mockito.reset(userRepository);
        Mockito.reset(roleRepository);

        Role managerRole = Role.builder()
                .id(1)
                .role("MANAGER")
                .build();

        Role customerRole = Role.builder()
                .id(2)
                .role("CUSTOMER")
                .build();

        User user_1 = User.builder()
                .id(1)
                .username("user_1")
                .password("123456")
                .email("mail_1@mail.com")
                .phoneNumber("1")
                .isBlocked(true)
                .roles(Arrays.asList(customerRole))
                .orders(new ArrayList<>())
                .build();

        User user_2 = User.builder()
                .id(2)
                .username("user_2")
                .password("123456")
                .email("mail_2@mail.com")
                .phoneNumber("2")
                .isBlocked(false)
                .orders(new ArrayList<>())
                .roles(Arrays.asList(managerRole))
                .build();


        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2));

        Mockito.when(roleRepository.findByRole("CUSTOMER")).thenReturn(customerRole);
        Mockito.when(userRepository.findUserById(user_1.getId())).thenReturn(Optional.of(user_1));
        Mockito.when(userRepository.findUsersByRolesContains(customerRole)).thenReturn(Arrays.asList(user_1));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showCustomersSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/customer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is("user_1")));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showCustomerSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/customer/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user_1")));
    }
}