package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.request.AuthenticationRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("logout")
public class LogoutController {

    private RestTemplate rest = new RestTemplate();

    private AuthenticationRequest authenticationRequest = null;

    @GetMapping("khachhang")
    private String homeKhachhang(Model model, HttpSession session) {
        session.removeAttribute("Token");
        return "redirect:/login";
    }

    @GetMapping("taixe")
    private String homeTaixe(Model model, HttpSession session) {
        session.removeAttribute("Token");
        return "redirect:/logintaixe";
    }

}
