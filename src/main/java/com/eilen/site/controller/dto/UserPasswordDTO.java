package com.eilen.site.controller.dto;

import lombok.Data;

@Data
public class UserPasswordDTO {
    private String username;
    private Integer id;
    private String password;
    private String newPassword;
}
