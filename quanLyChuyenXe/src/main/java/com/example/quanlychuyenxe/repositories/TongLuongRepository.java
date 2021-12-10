package com.example.quanlychuyenxe.repositories;

import com.example.quanlychuyenxe.model.TongLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TongLuongRepository extends JpaRepository<TongLuong, Integer> {

    @Modifying
    @Query(value = "INSERT INTO tongluong (luong_hoa_hong, chuyen_xe_id, luong_co_ban_id) VALUES (?,?,?)", nativeQuery = true)
    void saveTongLuong(Long luong_hoa_hong, Integer chuyenXeId, Integer luongCoBanId);

    @Query(value = "SELECT tongluong.* FROM tongluong\n" +
            "JOIN luongcoban, chuyenxe\n" +
            "WHERE luongcoban.id = tongluong.luong_co_ban_id\n" +
            "AND chuyen_xe_id = chuyenxe.id\n" +
            "AND luongcoban.tai_xe_username = ?\n" +
            "AND month(thoi_gian_ket_thuc) = ?\n" +
            "AND year(thoi_gian_ket_thuc) = ?", nativeQuery = true)
    List<TongLuong> getTongLuongNow(String username, Integer month, Integer year);
}
