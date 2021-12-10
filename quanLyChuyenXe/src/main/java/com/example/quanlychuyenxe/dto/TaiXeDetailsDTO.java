package com.example.quanlychuyenxe.dto;

import com.example.quanlychuyenxe.model.KhachHang;
import com.example.quanlychuyenxe.model.TaiXe;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class TaiXeDetailsDTO extends org.springframework.security.core.userdetails.User{

    private TaiXe taiXe;

    public TaiXeDetailsDTO(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                           boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, TaiXe taiXe) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.taiXe = taiXe;
    }

}
