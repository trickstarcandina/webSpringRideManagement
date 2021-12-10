package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.XeKhach;
import com.example.quanlychuyenxe.repositories.XeKhachRepository;
import com.example.quanlychuyenxe.services.XeKhachService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class XeKhachServiceImpl implements XeKhachService {
    private final XeKhachRepository xeKhachRepository;

    @Override
    public Response create(XeKhach xeKhach) {
        return ResponseBuilder.ok(xeKhachRepository.save(xeKhach));
    }

    @Override
    public Response delete(String bienSo) {
        xeKhachRepository.deleteById(bienSo);
        return ResponseBuilder.ok(200, "Xóa thông tin xe khách thành công");
    }

    @Override
    public Response searchById(String bienSo) {
        return ResponseBuilder.ok(xeKhachRepository.findById(bienSo));
    }

    @Override
    public Response searchByName(String tenXeKhach) {
        return ResponseBuilder.ok(xeKhachRepository.findByTenXeKhachContaining (tenXeKhach));
    }

//    @Override
//    public Response showXeKhach(String id, Pageable pageable) {
//        if(id == null) {
//            return ResponseBuilder.ok(xeKhachRepository.findAllTuyenXe(pageable));
//        }
//        return ResponseBuilder.ok(xeKhachRepository.findById(id));
//    }
}
