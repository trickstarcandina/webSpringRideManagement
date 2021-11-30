package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.KhachHang;

public interface KhachHangService {
    Response create(KhachHang khachHang);
    Response update(KhachHang khachHang);
    Response delete(String cmtKhachHang);
    Response searchByName(String ten);
    Response searchById(String cmt);
}
