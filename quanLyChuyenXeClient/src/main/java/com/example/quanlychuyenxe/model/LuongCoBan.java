package com.example.quanlychuyenxe.model;

import com.example.quanlychuyenxe.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LuongCoBan extends BaseEntity {
    @NotNull(message = "Thiếu lương")
    private Long luong;
    @NotNull(message = "Thiếu phụ cấp")
    private Long phuCap;
    @NotEmpty(message = "Thiếu ghi chú")
    private String ghiChu;
    @NotNull(message = "Thiếu tháng lương")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date thangLuong;
    private String cmtTaiXe;
    private Boolean isEdit= false;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tongluong",
//            joinColumns = @JoinColumn(name = "luongcoban_id"),
//            inverseJoinColumns = @JoinColumn(name = "chuyenxe_id"))
//    private List<ChuyenXe> chuyenXeList;
}
