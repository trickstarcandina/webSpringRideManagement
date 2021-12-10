package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TuyenXe extends BaseEntity {
    @NotEmpty(message = "Thiếu điểm đầu")
    private String diemDau;
    @NotEmpty(message = "Thiếu điểm cuối")
    private String diemCuoi;
    @NotNull(message = "Thiếu độ dài")
    private Integer doDai;
    @NotNull(message = "Thiếu độ phức tạp của tuyến đường")
    private Integer doPhucTapCuaTuyenDuong; // 1 dễ, 2 vừa, 3 khó

    private Boolean isEdit = false;
}
