package com.example.server.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @NotBlank
    @Column(unique = true, name = "username")
    private String username;

    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;
}
