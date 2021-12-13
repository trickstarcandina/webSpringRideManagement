package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.request.AuthenticationRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("myadmin")
public class LoginAdminController {

    private RestTemplate rest = new RestTemplate();

    @GetMapping
    private String home(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        return "myadmin";
    }

    @PostMapping("loginAdmin")
    private String loginAdmin(Model model, @Valid @ModelAttribute("authenticationRequest") AuthenticationRequest authenticationRequest) {
        try {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/login",
                    HttpMethod.POST, new HttpEntity<>(authenticationRequest, null), ResponseBuilder.class);
            if(responseEntity.getStatusCode() == HttpStatus.OK) {
                if(responseEntity.getBody().getMessage().equals("true")) {
                    return "authen";
                }
                return "myadmin";
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return "myadmin";
    }

    @PostMapping("authen")
    private String authen(Model model, @Valid @ModelAttribute("authenticationRequest") AuthenticationRequest authenticationRequest, @RequestParam("code") String code) {
        try {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/api/admin/authen?code=" + code,
                    HttpMethod.POST, new HttpEntity<>(authenticationRequest, null), ResponseBuilder.class);
            if(responseEntity.getStatusCode() == HttpStatus.OK) {
                if(responseEntity.getBody().getData().equals("True")) {
                    return "redirect:/admin/khachhang";
                }
                return "myadmin";
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return "myadmin";
    }
}
