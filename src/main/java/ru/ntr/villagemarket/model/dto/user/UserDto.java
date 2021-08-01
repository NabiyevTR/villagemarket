package ru.ntr.villagemarket.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ntr.villagemarket.model.entity.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private Date regDate;

    private char gender;

    private boolean isBlocked;

    private String address;

    private String comments;

    private String phoneNumber;

    private String email;

    private List<String> roles;

}
