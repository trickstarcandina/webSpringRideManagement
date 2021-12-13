package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntityUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "khachhang")
@Setter
@Getter
@Data
public class KhachHang extends BaseEntityUser {
    private String ten;
    private String diaChi;
    private Integer tuoi;
    private String ghiChu;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "khachhang_chuyenxe",
            joinColumns = @JoinColumn(name = "khach_hang_username"),
            inverseJoinColumns = @JoinColumn(name = "chuyenxe_id"))
    private Set<ChuyenXe> chuyenXeList;
}
