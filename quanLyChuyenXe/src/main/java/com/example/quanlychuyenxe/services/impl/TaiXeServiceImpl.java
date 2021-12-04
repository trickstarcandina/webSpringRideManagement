package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.services.TaiXeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class TaiXeServiceImpl implements TaiXeService {
    private final TaiXeRepository taiXeRepository;

    @Override
    public Response create(TaiXe taiXe) {
        return ResponseBuilder.ok(taiXeRepository.save(taiXe));
    }

    @Override
    public Response delete(String cmtTaiXe) {
        taiXeRepository.deleteById(cmtTaiXe);
        return ResponseBuilder.ok();
    }

    @Override
    public Response searchByName(String ten) {
        return ResponseBuilder.ok(taiXeRepository.findByTenContaining(ten));
    }

    @Override
    public Response searchById(String cmtTaiXe) {
        return ResponseBuilder.ok(taiXeRepository.findById(cmtTaiXe));
    }
}
