package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class LuongCoBan extends BaseEntity {
    private Long luong;
    private Long phuCap;
    private String ghiChu;
    private Date thangLuong;

    private String cmtTaiXe;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tongluong",
//            joinColumns = @JoinColumn(name = "luongcoban_id"),
//            inverseJoinColumns = @JoinColumn(name = "chuyenxe_id"))
//    private List<ChuyenXe> chuyenXeList;
}
