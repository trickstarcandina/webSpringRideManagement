package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.TongLuong;
import com.example.quanlychuyenxe.model.TuyenXe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TuyenXeRepository extends JpaRepository<TuyenXe, Integer> {

    @Query(value = "select * from tuyenxe where diem_cuoi = ? and diem_cuoi = ?", nativeQuery = true)
    TuyenXe searchTuyenXeDiemDauDiemCuoi(String diemDau, String diemCuoi);

    @Query(value = "select * from tuyenxe", nativeQuery = true)
    Page<TuyenXe> findAllTuyenXe(Pageable pageable);


}
