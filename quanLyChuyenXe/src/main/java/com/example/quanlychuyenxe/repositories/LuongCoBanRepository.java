package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.LuongCoBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LuongCoBanRepository extends JpaRepository<LuongCoBan, Integer> {
    List<LuongCoBan> findByLuongContaining(Long luong);
}
