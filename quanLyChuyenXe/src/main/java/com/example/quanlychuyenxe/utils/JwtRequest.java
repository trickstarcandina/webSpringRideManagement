package com.example.quanlychuyenxe.utils;

import com.example.quanlychuyenxe.dto.KhachHangDetailsDTO;
import com.example.quanlychuyenxe.dto.TaiXeDetailsDTO;
import com.example.quanlychuyenxe.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConfig.JWT_HEADER);
        if (header != null && header.startsWith(JwtConfig.JWT_TOKEN_PREFIX)){
            String token = header.substring(JwtConfig.JWT_TOKEN_PREFIX.length());
            String username = JwtUtils.extractUsername(token);
//            UserDetailsDTO userDetailsDTO = (UserDetailsDTO) userService.loadUserByUsername(username);
//            if (JwtUtils.validateToken(token, userDetailsDTO)) {
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetailsDTO, null , userDetailsDTO.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }

            UserDetails userDetails = userService.loadUserByUsername(username);
            try {
                KhachHangDetailsDTO userDetailsDTO = (KhachHangDetailsDTO) userDetails;
                if (JwtUtils.validateToken(token, userDetailsDTO)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetailsDTO, null , userDetailsDTO.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            catch (Exception e) {
                System.out.println(e);
                TaiXeDetailsDTO userDetailsDTO = (TaiXeDetailsDTO) userDetails;
                if (JwtUtils.validateToken(token, userDetailsDTO)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetailsDTO, null , userDetailsDTO.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
