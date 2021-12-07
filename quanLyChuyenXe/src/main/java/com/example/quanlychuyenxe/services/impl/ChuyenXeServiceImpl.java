package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.repositories.ChuyenXeRepository;
import com.example.quanlychuyenxe.services.ChuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ChuyenXeServiceImpl implements ChuyenXeService {
    private final ChuyenXeRepository chuyenXeRepository;

    @Override
    public Response create(ChuyenXe chuyenXe) {
        return ResponseBuilder.ok(chuyenXeRepository.save(chuyenXe));
    }

    @Override
    public Response delete(Integer id) {
        chuyenXeRepository.deleteById(id);
        return ResponseBuilder.ok();
    }

    @Override
    public Response searchById(Integer id) {
        return ResponseBuilder.ok(chuyenXeRepository.findById(id));
    }

    @Override
    public Response getAll() {
        return ResponseBuilder.ok(chuyenXeRepository.findAll());
    }
}
