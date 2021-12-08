package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.request.ChuyenXeRequest;

public interface ChuyenXeService {
    Response create(ChuyenXeRequest chuyenXeRequest);
    Response delete(Integer id);
    Response searchById(Integer id);
    Response getAll();
    Response searchByTuyenXe(String diemDau, String diemCuoi);
}
