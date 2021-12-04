package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.TaiXe;

public interface TaiXeService {
    Response create(TaiXe taiXe);
    Response delete(String cmtTaiXe);
    Response searchByName(String ten);
    Response searchById(String cmtTaiXe);
}
