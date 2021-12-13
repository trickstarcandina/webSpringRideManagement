package com.example.quanlychuyenxe.model.request;

import com.example.quanlychuyenxe.model.TaiXe;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TKTaiXeRequest {
    private TaiXe taiXe;
    private Integer laixe;
    private Integer phuxe;
}