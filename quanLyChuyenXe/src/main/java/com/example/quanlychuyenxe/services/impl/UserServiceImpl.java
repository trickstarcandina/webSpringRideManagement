package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.dto.KhachHangDetailsDTO;
import com.example.quanlychuyenxe.dto.TaiXeDetailsDTO;
import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.repositories.KhachHangRepository;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.services.UserService;
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
public class UserServiceImpl implements UserService {
    private final KhachHangRepository khachHangRepository;

    private final TaiXeRepository taiXeRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TaiXe taiXe = taiXeRepository.getUserName(s);
        if(taiXe != null) {
//            TaiXe taiXe = taiXeRepository.findById(s).get();
//            if(taiXe == null) {
//                throw new UsernameNotFoundException("Not found username : " + s);
//            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new TaiXeDetailsDTO(taiXe.getUsername(), taiXe.getPassword(), true, true,
                    true, true , authorities, taiXe);
        }
        KhachHang khachHang = khachHangRepository.findById(s).get();
        if(khachHang == null) {
            throw new UsernameNotFoundException("Not found username : " + s);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new KhachHangDetailsDTO(khachHang.getUsername(), khachHang.getPassword(), true, true,
                true, true , authorities, khachHang);
    }
}
