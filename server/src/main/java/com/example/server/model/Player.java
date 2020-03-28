package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PLAYER")
public class Player {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, name = "username")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid e-mail")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Column(name = "password")
    private String password;

}
