package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TongLuong extends BaseEntity {
    private ChuyenXe chuyenXe;

    private LuongCoBan luongCoBan;

    private Long luongHoaHong;

}
