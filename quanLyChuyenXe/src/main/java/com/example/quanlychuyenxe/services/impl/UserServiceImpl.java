package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
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

    private final TaiXeRepository taiXeRepository;
    private final KhachHangRepository khachHangRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        BaseEntityUser user = userRepository.findByUsername(username);
//        if (user == null)
//            throw new UsernameNotFoundException("Not found username : " + username);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        return new MyUserDetails(user.getUsername(), user.getPassword(), true, true,
//                true, true , authorities, user);
//    }

}
