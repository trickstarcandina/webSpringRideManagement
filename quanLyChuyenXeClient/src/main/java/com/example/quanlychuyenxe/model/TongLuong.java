package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class TongLuong extends BaseEntity {
    private ChuyenXe chuyenXe;

    private LuongCoBan luongCoBan;

    private Long luongHoaHong;

}
