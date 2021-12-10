package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.request.TongLuongRequest;

public interface TongLuongService {
    Response saveTongLuong(TongLuongRequest tongLuongRequest);
    Response getTongLuongByDate(String username, Integer month, Integer year);

    Response getAllTongLuongByDate(Integer thang, Integer nam);
}
