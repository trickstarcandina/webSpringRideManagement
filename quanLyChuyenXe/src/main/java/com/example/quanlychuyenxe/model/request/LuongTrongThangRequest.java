package com.example.quanlychuyenxe.model.request;

import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.TaiXe;
import lombok.Data;

import java.util.List;

@Data
public class LuongTrongThangRequest {
    private TaiXe taiXe;
    private Long luong;
    private List<ChuyenXe> chuyenXe;
}
