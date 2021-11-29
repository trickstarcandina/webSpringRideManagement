package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.repositories.ChuyenXeRepository;
import com.example.quanlychuyenxe.services.ChuyenXeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ChuyenXeServiceImpl implements ChuyenXeService {
    private final ChuyenXeRepository chuyenXeRepository;
}
