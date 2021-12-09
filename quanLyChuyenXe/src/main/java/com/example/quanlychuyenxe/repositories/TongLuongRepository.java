package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.TongLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TongLuongRepository extends JpaRepository<TongLuong, Integer> {

    @Modifying
    @Query(value = "INSERT INTO tongluong (luong_hoa_hong, chuyen_xe_id, luong_co_ban_id) VALUES (?,?,?)", nativeQuery = true)
    void saveTongLuong(Long luong_hoa_hong, Integer chuyenXeId, Integer luongCoBanId);
}
