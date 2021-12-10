package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.dto.KhachHangDetailsDTO;
import com.example.quanlychuyenxe.dto.TaiXeDetailsDTO;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.services.TaiXeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TaiXeServiceImpl implements TaiXeService {
    private final TaiXeRepository taiXeRepository;

    @Override
    public Response create(TaiXe taiXe) {
        if(taiXeRepository.checkUserNameExists(taiXe.getUsername()) > 0) {
            return ResponseBuilder.ok(201,"Tài khoản đã tồn tại, vui lòng thử lại");
        }
        else {
            return ResponseBuilder.ok(taiXeRepository.save(taiXe));
        }
    }

    @Override
    public Response update(TaiXe taiXe) {
        return ResponseBuilder.ok(taiXeRepository.save(taiXe));
    }

    @Override
    public Response delete(String username) {
        taiXeRepository.deleteById(username);
        return ResponseBuilder.ok(200,"Xóa tài xế thành công");
    }

    @Override
    public Response searchByName(String ten) {
        return ResponseBuilder.ok(taiXeRepository.findByTenContaining(ten));
    }

    @Override
    public Response searchById(String username) {
        return ResponseBuilder.ok(taiXeRepository.findById(username));
    }

}
