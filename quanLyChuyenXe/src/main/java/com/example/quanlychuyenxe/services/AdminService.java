package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;

import java.io.IOException;

public interface AdminService {
    Response findUser(String username, String password);
    Response authen(String username, String password, String code) throws IOException;
    Response showQRCode(String secretCode);
}
