package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.TaiXe;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TaiXeService {
    Response create(TaiXe taiXe);
    Response update(TaiXe taiXe);
    Response delete(String username);
    Response searchByName(String ten);
    Response searchById(String username);
}
