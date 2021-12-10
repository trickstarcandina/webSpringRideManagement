package com.example.quanlychuyenxe.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequest {

    @NotNull(message = "Username không được để trống")
    private String username;
    @NotNull(message = "Password không được để trống")
    private String password;

}
