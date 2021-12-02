package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.LuongCoBan;

public interface LuongCoBanService {
    Response create(LuongCoBan luongCoBan);
    Response delete(Integer id);
    Response searchById(Integer id);
    Response searchByLuong(Long luong);
}
