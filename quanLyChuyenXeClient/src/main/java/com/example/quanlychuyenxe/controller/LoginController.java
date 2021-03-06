package com.example.quanlychuyenxe.controller;

import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.model.ChuyenXe;
import com.example.quanlychuyenxe.model.KhachHang;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("login")
public class LoginController {

    private RestTemplate rest = new RestTemplate();

    private AuthenticationRequest authenticationRequest = null;

    @GetMapping
    private String home(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("khachhang")
    public String loginKhachHang(Model model, @Valid @ModelAttribute("authenticationRequest") AuthenticationRequest authenticationRequest, HttpSession session) {
        String notice = "Thành công";
        String fail = "Sai mật khẩu hoặc tài khoản";
        try {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/authenticateKhachHang",
                    HttpMethod.POST, new HttpEntity<>(authenticationRequest, null), ResponseBuilder.class);
            if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody().getData() != null) {
                model.addAttribute("notice", notice);
                session.setAttribute("Token", responseEntity.getBody().getData());
                return "redirect:/khachhang";
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        model.addAttribute("notice", fail);
        return "redirect:/login";
    }

}
