package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.XeKhach;
import com.example.quanlychuyenxe.repositories.XeKhachRepository;
import com.example.quanlychuyenxe.services.XeKhachService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class XeKhachServiceImpl implements XeKhachService {
    private final XeKhachRepository xeKhachRepository;

    @Override
    public Response create(XeKhach xeKhach) {
        return ResponseBuilder.ok (xeKhachRepository.save (xeKhach));
    }

    @Override
    public Response delete(String bienSo) {
        xeKhachRepository.deleteById (bienSo);
        return ResponseBuilder.ok ();
    }

    @Override
    public Response searchById(String bienSo) {
        return ResponseBuilder.ok (xeKhachRepository.findById (bienSo));
    }
}
