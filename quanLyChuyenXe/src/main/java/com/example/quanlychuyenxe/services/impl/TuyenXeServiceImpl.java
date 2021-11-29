package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.BaseResponse;
import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.repositories.TuyenXeRepository;
import com.example.quanlychuyenxe.services.TuyenXeService;
import lombok.AllArgsConstructor;
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
}
