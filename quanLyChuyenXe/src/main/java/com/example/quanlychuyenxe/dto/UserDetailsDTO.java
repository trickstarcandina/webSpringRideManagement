package com.example.quanlychuyenxe.dto;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import com.example.quanlychuyenxe.model.KhachHang;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UserDetailsDTO extends org.springframework.security.core.userdetails.User {
    private BaseEntityUser user;

    public UserDetailsDTO(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                          boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, BaseEntityUser user) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }
}
