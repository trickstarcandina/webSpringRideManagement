package com.example.quanlychuyenxe.services;

import com.example.quanlychuyenxe.base.response.BaseResponse;
import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.model.TuyenXe;
import org.springframework.data.domain.Pageable;

public interface TuyenXeService {
    Response create(TuyenXe tuyenXe);
    Response delete(String idTuyenXe);
    Response searchTuyenXeDiemDauDiemCuoi(String diemDau, String diemCuoi);
    Response showTuyenXe(String idTuyenXe, Pageable pageable);
    Response search(String diemDau, String diemCuoi);
    Response showTuyenXeById(Integer id);
    Response update(TuyenXe tuyenXe);
}
