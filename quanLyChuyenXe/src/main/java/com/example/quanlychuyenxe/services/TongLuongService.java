package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.request.TongLuongRequest;

public interface TongLuongService {
    Response saveTongLuong(TongLuongRequest tongLuongRequest);
}
