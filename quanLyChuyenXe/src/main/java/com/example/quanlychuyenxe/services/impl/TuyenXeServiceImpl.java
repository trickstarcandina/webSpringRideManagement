package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.BaseResponse;
import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.repositories.TuyenXeRepository;
import com.example.quanlychuyenxe.services.TuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class TuyenXeServiceImpl implements TuyenXeService {
    private final TuyenXeRepository tuyenXeRepository;

    @Override
    public Response create(TuyenXe tuyenXe) {
        return ResponseBuilder.ok(tuyenXeRepository.save(tuyenXe));
    }

    @Override
    public Response delete(String idTuyenXe) {
        tuyenXeRepository.deleteById(Integer.valueOf(idTuyenXe));
        return ResponseBuilder.ok(200, "Xóa tuyến xe thành công");
    }

    @Override
    public Response searchTuyenXeDiemDauDiemCuoi(String diemDau, String diemCuoi) {
        return ResponseBuilder.ok(tuyenXeRepository.searchTuyenXeDiemDauDiemCuoi(diemDau, diemCuoi));
    }

    @Override
    public Response showTuyenXe(String idTuyenXe, Pageable pageable) {
        if(idTuyenXe == null) {
            return ResponseBuilder.ok(tuyenXeRepository.findAllTuyenXe(pageable));
        }
        return ResponseBuilder.ok(tuyenXeRepository.findById(Integer.valueOf(idTuyenXe)));
    }
}
