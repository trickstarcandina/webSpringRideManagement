package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.ChuyenXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChuyenXeRepository extends JpaRepository<ChuyenXe, Integer> {
    List<ChuyenXe> findAllByTuyenXe_DiemDauContainingAndTuyenXe_DiemCuoiContaining(String diemDau, String diemCuoi);
}
