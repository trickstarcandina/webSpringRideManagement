package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.TaiXe;
import com.example.quanlychuyenxe.model.request.ChuyenXeRequest;
import com.example.quanlychuyenxe.model.TuyenXe;
import com.example.quanlychuyenxe.model.XeKhach;
import com.example.quanlychuyenxe.repositories.ChuyenXeRepository;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.repositories.TuyenXeRepository;
import com.example.quanlychuyenxe.repositories.XeKhachRepository;
import com.example.quanlychuyenxe.services.ChuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ChuyenXeServiceImpl implements ChuyenXeService {
    private final ChuyenXeRepository chuyenXeRepository;
    private final TuyenXeRepository tuyenXeRepository;
    private final XeKhachRepository xeKhachRepository;
    private final TaiXeRepository taiXeRepository;

    @Override
    public Response create(ChuyenXeRequest chuyenXeRequest) {
        ChuyenXe chuyenXe = new ChuyenXe();
        chuyenXe.setGiaVe(chuyenXeRequest.getGiaVe());
        chuyenXe.setThoiGianKhoiHanh(chuyenXeRequest.getThoiGianKhoiHanh());
        chuyenXe.setThoiGianKetThuc(chuyenXeRequest.getThoiGianKetThuc());
        chuyenXe.setSoLuongHanhKhach(chuyenXeRequest.getSoLuongHanhKhach());
        chuyenXe.setStatus(0);
        TuyenXe tuyenXe = tuyenXeRepository.findById(chuyenXeRequest.getTuyen_xe_id()).get();
        XeKhach xeKhach = xeKhachRepository.findById(chuyenXeRequest.getXe_khach_bien_so()).get();
        if(chuyenXeRequest.getCmtLaiXe() != null) {
            TaiXe laixe = taiXeRepository.findById(chuyenXeRequest.getCmtLaiXe()).get();
            chuyenXe.setTaiXe1(laixe);
        }
        if(chuyenXeRequest.getCmtPhuXe() != null) {
            TaiXe phuxe = taiXeRepository.findById(chuyenXeRequest.getCmtPhuXe()).get();
            chuyenXe.setTaiXe2(phuxe);
        }

        if(ObjectUtils.isEmpty(tuyenXe) || ObjectUtils.isEmpty(xeKhach)) {
            return ResponseBuilder.ok(100, "Sai mã xe khách hoặc tuyến xe");
        }
        if(chuyenXeRequest.getId() != null) chuyenXe.setId(chuyenXeRequest.getId());
        chuyenXe.setTuyenXe(tuyenXe);
        chuyenXe.setXeKhach(xeKhach);
        return ResponseBuilder.ok(chuyenXeRepository.save(chuyenXe));
    }

    @Override
    public Response delete(Integer id) {
        chuyenXeRepository.deleteById(id);
        return ResponseBuilder.ok();
    }

    @Override
    public Response searchById(Integer id) {
        return ResponseBuilder.ok(chuyenXeRepository.findById(id));
    }

    @Override
    public Response getAll() {
        return ResponseBuilder.ok(chuyenXeRepository.findAll());
    }

    @Override
    public Response searchByTuyenXe(String diemDau, String diemCuoi) {
        return ResponseBuilder.ok(chuyenXeRepository.findAllByTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContaining(diemDau, diemCuoi));
    }
}
