package com.example.quanlychuyenxe.utils;

import com.example.quanlychuyenxe.dto.KhachHangDetailsDTO;
import com.example.quanlychuyenxe.services.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequest extends OncePerRequestFilter {

    private KhachHangService khachHangService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConfig.JWT_HEADER);
        if (header != null && header.startsWith(JwtConfig.JWT_TOKEN_PREFIX)){
            String token = header.substring(JwtConfig.JWT_TOKEN_PREFIX.length());
            String username = JwtUtils.extractUsername(token);
            KhachHangDetailsDTO khachHangDetailsDTO = (KhachHangDetailsDTO) khachHangService.loadUserByUsername(username);
            if (JwtUtils.validateToken(token, khachHangDetailsDTO)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(khachHangDetailsDTO, null , khachHangDetailsDTO.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
