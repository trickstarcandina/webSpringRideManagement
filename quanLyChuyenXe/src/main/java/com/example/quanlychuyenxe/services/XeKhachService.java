package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.XeKhach;
import org.springframework.data.domain.Pageable;

public interface XeKhachService {
    Response create(XeKhach khachHang);
    Response delete(String bienSo);
    Response searchById(String bienSo);
    Response showXeKhach(String idTuyenXe, Pageable pageable);
    Response searchByName(String tenxekhach);
}
