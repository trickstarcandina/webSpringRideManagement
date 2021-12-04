package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.repositories.KhachHangRepository;
import com.example.quanlychuyenxe.services.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepository khachHangRepository;

    @Override
    public Response create(KhachHang khachHang) {
        return ResponseBuilder.ok(khachHangRepository.save(khachHang));
    }

    @Override
    public Response delete(String cmtKhachHang) {
        khachHangRepository.deleteById(cmtKhachHang);
        return ResponseBuilder.ok();
    }

    @Override
    public Response searchByName(String ten) {
        return ResponseBuilder.ok(khachHangRepository.findByTenContaining(ten));
    }

    @Override
    public Response searchById(String cmt) {
        return ResponseBuilder.ok(khachHangRepository.findById(cmt));
    }
}
