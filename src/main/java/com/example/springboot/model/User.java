package com.example.springboot.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
}
