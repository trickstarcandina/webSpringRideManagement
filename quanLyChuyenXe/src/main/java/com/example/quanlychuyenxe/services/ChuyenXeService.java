package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.request.ChuyenXeRequest;

public interface ChuyenXeService {
    // Admin
    Response create(ChuyenXeRequest chuyenXeRequest);
    Response delete(Integer id);
    Response searchById(Integer id);
    Response getAll();
    Response searchByTuyenXe(String diemDau, String diemCuoi);
    Response thongkeChuyenXe();
    Response thongkeTaiXe();

    // Lái xe
    Response searchLaiXeByStatus(String username, Integer status);
    Response updateStatus(Integer id, Integer status);
    Response findChuyenXeThieuTaiXe(String diemDau, String diemCuoi);
    Response updateLaiXeById(Integer id, String cmt);
    Response updatePhuXeById(Integer id, String cmt);

    // Khách hàng
    Response findChuyenXeByStatus(String diemDau, String diemCuoi, Integer status);
    Response update(ChuyenXe chuyenXe);
    Response allKhachHang(Integer id);
    Response updateKhachHang(String username, Integer id);

}
