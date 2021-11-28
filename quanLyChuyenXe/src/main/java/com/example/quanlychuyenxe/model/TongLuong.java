package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.model.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tongluong")
@Setter
@Getter
@Data
public class TongLuong extends BaseEntity {
    @ManyToOne
    private ChuyenXe chuyenXe;

    @ManyToOne
    private LuongCoBan luongCoBan;

    private Long luongHoaHong;

}
