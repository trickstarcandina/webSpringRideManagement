package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.ChuyenXe;

public interface ChuyenXeService {
    Response create(ChuyenXe chuyenXe);
    Response delete(Integer id);
    Response searchById(Integer id);
    Response getAll();
}
