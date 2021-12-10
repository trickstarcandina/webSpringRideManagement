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

import javax.validation.Valid;

@Controller
@RequestMapping("logintaixe")
public class LoginTaiXeController {

    private RestTemplate rest = new RestTemplate();

    @GetMapping
    private String home(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        return "logintaixe";
    }

    @PostMapping("taixe")
    public String loginKhachHang(Model model, @Valid @ModelAttribute("authenticationRequest") AuthenticationRequest authenticationRequest) {
        String notice = "Thành công";
        String fail = "Sai mật khẩu hoặc tài khoản";

        try {
            ResponseEntity<ResponseBuilder> responseEntity = rest.exchange("http://localhost:8080/authenticateTaiXe",
                    HttpMethod.POST, new HttpEntity<>(authenticationRequest, null), ResponseBuilder.class);
            if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody().getData() != null) {
                model.addAttribute("notice", notice);
                return "redirect:/taixe";
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        model.addAttribute("notice", fail);
        return "logintaixe";
    }
}
