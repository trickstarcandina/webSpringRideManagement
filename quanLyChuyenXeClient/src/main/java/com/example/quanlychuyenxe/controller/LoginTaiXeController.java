package com.example.quanlychuyenxe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("logintaixe")
public class LoginTaiXeController {

    @GetMapping
    private String home() {
        return "logintaixe";
    }
}
