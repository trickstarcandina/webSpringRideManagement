package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.dto.KhachHangDetailsDTO;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.repositories.KhachHangRepository;
import com.example.quanlychuyenxe.services.KhachHangService;
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
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepository khachHangRepository;

    @Override
    public Response create(KhachHang khachHang) {
        if(khachHangRepository.checkUserNameExists(khachHang.getUsername()) > 0) {
            return ResponseBuilder.ok(201,"Tài khoản đã tồn tại, vui lòng thử lại");
        }
        else {
            return ResponseBuilder.ok(khachHangRepository.save(khachHang));
        }
    }

    @Override
    public Response delete(String username) {
        khachHangRepository.deleteById(username);
        return ResponseBuilder.ok(200,"Xóa khách hàng thành công");
    }

    @Override
    public Response searchByName(String ten) {
        return ResponseBuilder.ok(khachHangRepository.findByTenContaining(ten));
    }

    @Override
    public Response searchById(String username) {
        return ResponseBuilder.ok(khachHangRepository.findById(username));
    }

}
