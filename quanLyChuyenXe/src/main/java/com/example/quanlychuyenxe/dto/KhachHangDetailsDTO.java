package com.example.quanlychuyenxe.dto;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import com.example.quanlychuyenxe.model.KhachHang;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class KhachHangDetailsDTO extends org.springframework.security.core.userdetails.User {

    private KhachHang khachHang;

    public KhachHangDetailsDTO(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                               boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, KhachHang khachHang) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.khachHang = khachHang;
    }
}
