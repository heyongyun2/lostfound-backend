package org.example.springboot.controller;

import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.example.springboot.util.JwtUtil;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JavaMailSender mailSender;

    // 临时存储邮箱和验证码
    public static Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    // ==================== 1. 发送验证码接口 ====================
    @PostMapping("/api/send-code")
    public ResponseEntity<Map<String, Object>> sendCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, Object> response = new HashMap<>();

        if (email == null || !email.endsWith("@segi4u.my")) {
            response.put("code", 400);
            response.put("message", "Please enter a valid SEGI email");
            return ResponseEntity.status(400).body(response);
        }

        try {
            // 生成 6 位随机验证码
            String code = String.format("%06d", new Random().nextInt(999999));

            // 发送邮件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("2214550371@qq.com");
            message.setTo(email);
            message.setSubject("【SEGI System】Verification Code");
            message.setText("Your verification code is: " + code + ", valid for 5 minutes.");
            mailSender.send(message);

            // 存入缓存
            verificationCodes.put(email, code);

            response.put("code", 200);
            response.put("message", "Verification code sent successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 500);
            response.put("message", "Failed to send email: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // ==================== 2. 注册接口 ====================
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        String email = user.getEmail();
        String inputCode = user.getCode(); // 前端传过来的验证码

        // 1. 【第一步：校验验证码】
        String realCode = verificationCodes.get(email);
        if (realCode == null || !realCode.equals(inputCode)) {
            response.put("code", 400);
            response.put("message", "Verification code error or expired"); // 验证码错误或失效
            return ResponseEntity.status(400).body(response);
        }

        // 2. 【第二步：执行注册逻辑】（内部会检查学号或邮箱是否已存在）
        boolean success = userService.register(user);

        if (success) {
            // 注册成功后，清空用过的验证码缓存
            verificationCodes.remove(email);

            response.put("code", 200);
            response.put("message", "Account registered successfully");
            return ResponseEntity.ok(response);
        } else {
            // 数据库注册失败（说明学号或邮箱已经存在）
            response.put("code", 400);
            response.put("message", "This account has already been registered"); // 账号已存在
            return ResponseEntity.status(400).body(response);
        }
    }

    // ==================== 3. 登录接口 ====================
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginMap) {
        String studentId = loginMap.get("studentId");
        String password = loginMap.get("password");

        User user = userService.login(studentId, password);
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            String token = jwtUtil.Createtoken(user.getUserId().longValue());
            response.put("code", 200);
            response.put("message", "登录成功");
            response.put("token", token);
            response.put("studentId", user.getStudentId());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());// user 或 admin
            response.put("userId", user.getUserId());
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 401);
            response.put("message", "Login failed: Invalid student ID or password");
            return ResponseEntity.status(401).body(response);
        }
    }
    // ==================== 1. 发送重置密码验证码接口 ====================
    @PostMapping("/auth/send-reset-code")
    public ResponseEntity<Map<String, Object>> sendResetCode(@RequestBody Map<String, String> requestMap) {
        String email = requestMap.get("email");
        Map<String, Object> response = new HashMap<>();

        // 检查该邮箱是否在数据库中注册过（你可以调用你的 userService 或直接用 mapper/repository 查）
        User user = userService.findByEmail(email); // 确保你的 service 里有这个方法，或者用你的查询方式
        if (user == null) {
            response.put("code", 400);
            response.put("message", "This email is not registered!");
            return ResponseEntity.status(400).body(response);
        }

        // 生成随机 6 位验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        try {
            // 调用你之前写好的邮件发送工具类/方法
            // 假设你有一个 mailService.sendEmail(...) 或者 JavaMailSender

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("2214550371@qq.com");
            message.setTo(email);
            message.setSubject("Lost & Found App - Password Reset Verification Code");
            message.setText("Your verification code is: " + code + ". Valid for 5 minutes.");
            mailSender.send(message); // 如果你的注入名不同，请对应修改
            verificationCodes.put(email, code);
            // 既然你的 User 实体类里有 code 字段，你可以把验证码存到该用户的数据库记录中（或者存入 Redis）
            user.setCode(code);
            userService.updateUser(user); // 保存验证码到数据库

            response.put("code", 200);
            response.put("message", "Verification code sent successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 500);
            response.put("message", "Failed to send email");
            return ResponseEntity.status(500).body(response);
        }
    }

    // ==================== 2. 校验验证码并重置密码接口 ====================
    // ==================== 校验验证码并重置密码 ====================
    @PostMapping("/auth/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> requestMap) {
        String email = requestMap.get("email");
        String inputCode = requestMap.get("code");
        String newPassword = requestMap.get("newPassword");
        Map<String, Object> response = new HashMap<>();

        // 1. 从内存 Map 中校验验证码（和你的注册接口逻辑完全一致）
        String realCode = verificationCodes.get(email);
        if (realCode == null || !realCode.equals(inputCode)) {
            response.put("code", 400);
            response.put("message", "Verification code error or expired");
            return ResponseEntity.status(400).body(response);
        }

        // 2. 根据邮箱查询用户
        User user = userService.findByEmail(email);
        if (user == null) {
            response.put("code", 400);
            response.put("message", "User not found!");
            return ResponseEntity.status(400).body(response);
        }

        // 3. 验证通过，直接更新密码（只改密码，不碰数据库的 code 字段）
        user.setPassword(newPassword);
        userService.updateUser(user);
        user.setCode(null);

        // 4. 验证码使用完毕，从内存中清除
        verificationCodes.remove(email);

        response.put("code", 200);
        response.put("message", "Password reset successfully!");
        return ResponseEntity.ok(response);
    }
}
