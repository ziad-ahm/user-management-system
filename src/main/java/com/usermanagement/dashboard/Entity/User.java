package com.usermanagement.dashboard.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    private String jobTitle;

    private Double salary;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public enum Role {
        ADMIN,
        ACCOUNTANT,
        USER
    }
}
