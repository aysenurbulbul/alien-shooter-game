package com.example.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {
    private Long id;
    private String username;
    private String email;
    private String password;

    // jwt token for authentication and authorization
    @JsonIgnore
    private String jwt;

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

}
