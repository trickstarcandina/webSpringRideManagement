package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.KhachHang;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface KhachHangService {
    Response create(KhachHang khachHang);
    Response delete(String username);
    Response searchByName(String ten);
    Response searchById(String username);
    Response update(KhachHang khachHang);

    public Response getKhachHangChuyenXe(String username);
}
