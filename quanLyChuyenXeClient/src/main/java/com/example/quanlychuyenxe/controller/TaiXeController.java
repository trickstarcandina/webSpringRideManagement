package com.example.quanlychuyenxe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("taixe")
public class TaiXeController {

    private RestTemplate rest = new RestTemplate();

    @GetMapping("")
    private String home() {
        return "taixe/home";
    }
}
