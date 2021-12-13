package com.example.quanlychuyenxe.services.impl;

import com.example.quanlychuyenxe.base.response.Response;
import com.example.quanlychuyenxe.base.response.ResponseBuilder;
import com.example.quanlychuyenxe.repositories.AdminRepository;
import com.example.quanlychuyenxe.repositories.TaiXeRepository;
import com.example.quanlychuyenxe.services.AdminService;
import com.example.quanlychuyenxe.utils.CryptLib;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.jsoup.nodes.Document;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import javax.ws.rs.core.UriBuilder;

@Service
@AllArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Response findUser(String username, String password) {
        if(adminRepository.checkExist(username, password) > 0) {
            return ResponseBuilder.ok(200, "true");
        }
        return ResponseBuilder.ok(201, "false");
    }

    @Override
    public Response authen(String username, String password, String code) throws IOException {
        String sercetkey = CryptLib.encryptPositiveLong(adminRepository.findDisplayName(username, password), 7891656);
//        RestTemplate restTemplate = new RestTemplate();
//        URI uri = UriBuilder.fromUri("https://www.authenticatorApi.com/Validate.aspx")
//                .queryParam("Pin", code)
//                .queryParam("SecretCode", adminRepository.findDisplayName(username, password))
//                .build();
//       ?Pin=123456&SecretCode=12345678BXYT
//        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), Map.class);
        String url = "https://www.authenticatorApi.com/Validate.aspx?Pin=" + code + "&SecretCode=" + sercetkey;
//        Document doc = Jsoup.connect(url).get();
//        return ResponseBuilder.ok(doc.childNodes().get(0).parentNode().);
        return ResponseBuilder.ok(getText(url));
    }

    @Override
    public Response showQRCode(String displayName) {
        String sercetkey = CryptLib.encryptPositiveLong(displayName, 7891656);
//        https://www.authenticatorApi.com/pair.aspx?AppName=MyApp&AppInfo=John&SecretCode=12345678BXYT
//        URI uri = UriBuilder.fromUri("https://www.authenticatorApi.com/pair.aspx")
//                .queryParam("AppName", "QuanLyChuyenXe")
//                .queryParam("AppInfo", displayName)
//                .queryParam("SecretCode", displayName)
//                .build();
        String url = "https://www.authenticatorApi.com/pair.aspx?AppName=QuanLyChuyenXe&AppInfo=" + displayName + "&SecretCode=" + sercetkey;

//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), Map.class);
        return ResponseBuilder.ok(url);
    }

    private String getText(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        //add headers to the connection, or check the status if desired..

        // handle error response code it occurs
        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        if (200 <= responseCode && responseCode <= 299) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        inputStream));

        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();

        return response.toString();
    }
}
