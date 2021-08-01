package ru.ntr.villagemarket.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private String password;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private char gender;

    private String address;

    private String phoneNumber;

    private String email;
}
