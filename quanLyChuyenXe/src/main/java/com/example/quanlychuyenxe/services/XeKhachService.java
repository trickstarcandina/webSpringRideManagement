package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.XeKhach;

public interface XeKhachService {
    Response create(XeKhach khachHang);
    Response delete(String bienSo);

    Response searchById(String bienSo);
}
