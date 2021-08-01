package ru.ntr.villagemarket.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "public")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // @JsonFormat(pattern="dd.MM.yyyy")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "gender")
    private char gender;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "address")
    private String address;

    @Column(name = "comments")
    private String comments;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_users_roles_user")
    )
    private List<Role> roles;


    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Cart cart;

}
