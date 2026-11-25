package org.example.springboot.controller;

import org.example.springboot.entity.Claim;
import org.example.springboot.mapper.ClaimMapper; // 或者通过 Service 注入
import org.example.springboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
public class ClaimController {

    @Autowired
    private ClaimMapper claimMapper;
    @Autowired
    private JwtUtil jwtUtil;
    // 直接在注解里写完整的路径，不加类前缀
    @PostMapping("/api/claim/submit")
    public Map<String, Object> submitClaim(@RequestBody Claim claim) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 如果不需要从 Token 解析，直接校验前端传过来的 userId 是否为空
            if (claim.getUserId() == null) {
                response.put("code", 401);
                response.put("msg", "Unauthorized: Please login first");
                return response;
            }

            claim.setStatus("pending");
            int rows = claimMapper.insertClaim(claim);

            if (rows > 0) {
                response.put("code", 200);
                response.put("msg", "Success");
            } else {
                response.put("code", 500);
                response.put("msg", "Insert failed");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("msg", "Error: " + e.getMessage());
        }
        return response;
    }
}