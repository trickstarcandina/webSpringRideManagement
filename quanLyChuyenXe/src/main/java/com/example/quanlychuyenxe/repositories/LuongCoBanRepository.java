package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.LuongCoBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LuongCoBanRepository extends JpaRepository<LuongCoBan, Integer> {
}
