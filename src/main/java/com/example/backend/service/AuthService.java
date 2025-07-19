package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private SmsService smsService;
    
    private static final SecureRandom secureRandom = new SecureRandom();
    
    // 用户注册
    public boolean register(String phone, String password, String verificationCode) {
        // 验证验证码
        if (!smsService.verifyCode(phone, 1, verificationCode)) {
            return false;
        }
        
        // 检查手机号是否已注册
        if (userRepository.findByPhone(phone).isPresent()) {
            return false;
        }
        
        // 生成盐值
        String salt = generateSalt();
        
        // 创建用户
        User user = new User();
        user.setPhone(phone);
        user.setSalt(salt);
        user.setPassword(encodePassword(password, salt));
        user.setStatus(1);
        user.setCreateTime(java.time.LocalDateTime.now());
        user.setUpdateTime(java.time.LocalDateTime.now());
        
        userRepository.save(user);
        return true;
    }
    
    // 用户登录
    public boolean login(String phone, String password) {
        Optional<User> userOptional = userRepository.findByPhone(phone);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // 检查用户状态
            if (user.getStatus() != 1) {
                return false;
            }
            
            // 验证密码
            return passwordEncoder.matches(password + user.getSalt(), user.getPassword());
        }
        
        return false;
    }
    
    // 重置密码
    public boolean resetPassword(String phone, String newPassword, String verificationCode) {
        // 验证验证码
        if (!smsService.verifyCode(phone, 2, verificationCode)) {
            return false;
        }
        
        Optional<User> userOptional = userRepository.findByPhone(phone);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // 生成新盐值
            String newSalt = generateSalt();
            
            // 更新密码和盐值
            user.setSalt(newSalt);
            user.setPassword(encodePassword(newPassword, newSalt));
            user.setUpdateTime(java.time.LocalDateTime.now());
            
            userRepository.save(user);
            return true;
        }
        
        return false;
    }
    
    // 生成盐值
    private String generateSalt() {
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    // 密码加密
    private String encodePassword(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }
}    