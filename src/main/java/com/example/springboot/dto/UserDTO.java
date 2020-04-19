package com.example.springboot.dto;

import lombok.Data;

/**
 * 封装User
 */
@Data
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String code;
}
