package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.request.LuongCoBanRequest;

public interface LuongCoBanService {
    Response create(LuongCoBanRequest luongCoBanRequest);
    Response delete(Integer id);
    Response searchById(Integer id);
    Response findLuongByTaiXe(String username);

    Response findLaterLuong(String username);
}
