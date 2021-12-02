package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.TongLuong;
import com.example.quanlychuyenxe.model.TuyenXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TuyenXeRepository extends JpaRepository<TuyenXe, Integer> {
    List<TuyenXe> findByDiemDau(String diemDau);
}
