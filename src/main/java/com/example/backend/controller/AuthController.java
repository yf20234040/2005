package com.example.backend.controller;

import com.example.backend.service.AuthService;
import com.example.backend.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private SmsService smsService;
    
    // 发送注册验证码
    @PostMapping("/sendRegisterCode")
    public Map<String, Object> sendRegisterCode(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        boolean result = smsService.sendVerificationCode(phone, 1);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        response.put("message", result ? "验证码已发送" : "发送失败，请稍后再试");
        return response;
    }
    
    // 发送重置密码验证码
    @PostMapping("/sendResetPasswordCode")
    public Map<String, Object> sendResetPasswordCode(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        boolean result = smsService.sendVerificationCode(phone, 2);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        response.put("message", result ? "验证码已发送" : "发送失败，请稍后再试");
        return response;
    }
    
    // 用户注册
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String password = request.get("password");
        String code = request.get("code");
        
        boolean result = authService.register(phone, password, code);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        response.put("message", result ? "注册成功" : "注册失败，手机号已存在或验证码错误");
        return response;
    }
    
    // 用户登录
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String password = request.get("password");
        
        boolean result = authService.login(phone, password);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        response.put("message", result ? "登录成功" : "登录失败，手机号或密码错误");
        return response;
    }
    
    // 重置密码
    @PostMapping("/resetPassword")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String newPassword = request.get("newPassword");
        String code = request.get("code");
        
        boolean result = authService.resetPassword(phone, newPassword, code);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", result);
        response.put("message", result ? "密码重置成功" : "重置失败，手机号或验证码错误");
        return response;
    }
}    