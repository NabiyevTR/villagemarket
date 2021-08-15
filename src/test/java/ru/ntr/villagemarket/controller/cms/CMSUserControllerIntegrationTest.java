package ru.ntr.villagemarket.controller.cms;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ntr.villagemarket.controller.AbstractControllerIntegrationTest;
import ru.ntr.villagemarket.exceptions.NoSuchUserException;
import ru.ntr.villagemarket.model.dto.user.NewUserDto;
import ru.ntr.villagemarket.model.dto.user.UserDto;
import ru.ntr.villagemarket.model.entity.Role;
import ru.ntr.villagemarket.model.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyString;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CMSUserControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();


        Role managerRole = Role.builder()
                .id(1)
                .role("MANAGER")
                .build();

        Role customerRole = Role.builder()
                .id(1)
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
                .build();

        User user_2 = User.builder()
                .id(2)
                .username("user_2")
                .password("123456")
                .email("mail_2@mail.com")
                .phoneNumber("2")
                .isBlocked(false)
                .roles(Arrays.asList(managerRole))
                .build();


        List<User> users = new ArrayList<>(Arrays.asList(user_1, user_2));

        Mockito.reset(userRepository);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userRepository.findUserById(user_1.getId())).thenReturn(Optional.of(user_1));
        Mockito.when(userRepository.findUserById(user_2.getId())).thenReturn(Optional.of(user_2));

        Mockito.when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(user_2));
    }


    @Test
    @WithMockUser(username = "spring", roles = {"ADMIN"})
    void showUsersSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("user_1")))
                .andExpect(jsonPath("$[1].username", is("user_2")));
    }


    @Test
    @WithMockUser(username = "spring", roles = {"ADMIN"})
    void showUserSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user_1")));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"ADMIN"})
    void showUserNegative() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/user/{id}", 10)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchUserException));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"ADMIN"})
    void createSuccess() throws Exception {

        Mockito.when(userRepository.findUserByUsername("user_3")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByEmail("user_3")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByPhoneNumber("user_3")).thenReturn(Optional.empty());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        NewUserDto userDto = NewUserDto.builder()
                .username("user_3")
                .password("123456")
                .email("3")
                .phoneNumber("4")
                .roles(Arrays.asList("CUSTOMER"))
                .build();

        mockMvc.perform(post("/api/cms/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "spring", roles = {"ADMIN"})
    void updateUserSuccess() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        UserDto userDto = UserDto.builder()
                .username("user_3")
                .password("123456")
                .email("3")
                .phoneNumber("4")
                .roles(Arrays.asList("CUSTOMER"))
                .build();

        mockMvc.perform(patch("/api/cms/user/{id}/edit", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "spring", roles = {"ADMIN"})
    void deleteUserSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/cms/user/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}