package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.model.request.AuthenticationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("myadmin")
public class LoginAdminController {

    private RestTemplate rest = new RestTemplate();

    @GetMapping
    private String home(Model model) {
        return "myadmin";
    }

}
