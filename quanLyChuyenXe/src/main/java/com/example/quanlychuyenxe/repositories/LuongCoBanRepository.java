package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.LuongCoBan;
import com.example.quanlychuyenxe.model.request.LuongCoBanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LuongCoBanRepository extends JpaRepository<LuongCoBan, Integer> {

    @Query(value = "SELECT * FROM luongcoban WHERE tai_xe_username = ? ORDER BY thang_luong desc LIMIT 1", nativeQuery = true)
    LuongCoBan findLuongCoBanByTaiXe_Username(String username);

    @Query(value = "SELECT luongcoban.id FROM luongcoban WHERE tai_xe_username = ? ORDER BY thang_luong desc LIMIT 1", nativeQuery = true)
    Integer getLuongCoBanByUsername(String username);

    @Query(value = "SELECT luong FROM luongcoban WHERE tai_xe_username = ? ORDER BY thang_luong desc LIMIT 1", nativeQuery = true)
    Long getLuongCuoiByUsername(String username);
}
