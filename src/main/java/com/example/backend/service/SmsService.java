package com.example.backend.service;

import com.example.backend.entity.SmsCode;
import com.example.backend.repository.SmsCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsService {

    @Autowired
    private SmsCodeRepository smsCodeRepository;
    
    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;
    
    // 发送验证码
    public boolean sendVerificationCode(String phone, Integer type) {
        // 检查是否已存在未过期的验证码
        if (smsCodeRepository.existsByPhoneAndTypeAndExpireTimeAfter(phone, type, LocalDateTime.now())) {
            return false; // 已有未过期验证码
        }
        
        // 生成6位随机验证码
        String code = generateCode();
        
        // 保存验证码到数据库，有效期5分钟
        SmsCode smsCode = new SmsCode();
        smsCode.setPhone(phone);
        smsCode.setCode(code);
        smsCode.setType(type);
        smsCode.setExpireTime(LocalDateTime.now().plusMinutes(5));
        smsCodeRepository.save(smsCode);
        
        // 这里调用实际的短信发送服务
        return sendSms(phone, code);
    }
    
    // 验证验证码
    public boolean verifyCode(String phone, Integer type, String code) {
        Optional<SmsCode> smsCodeOptional = smsCodeRepository.findFirstByPhoneAndTypeOrderByCreateTimeDesc(phone, type);
        
        if (smsCodeOptional.isPresent()) {
            SmsCode smsCode = smsCodeOptional.get();
            return smsCode.getCode().equals(code) && 
                   smsCode.getExpireTime().isAfter(LocalDateTime.now());
        }
        
        return false;
    }
    
    // 生成随机验证码
    private String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
    // 实际发送短信的方法，需要集成短信服务商API
    private boolean sendSms(String phone, String code) {
        // 这里应该集成实际的短信服务提供商API
        System.out.println("模拟发送短信到 " + phone + "，验证码：" + code);
        return true;
    }
}    