package com.example.backend.repository;

import com.example.backend.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    Optional<SmsCode> findFirstByPhoneAndTypeOrderByCreateTimeDesc(String phone, Integer type);
    
    boolean existsByPhoneAndTypeAndExpireTimeAfter(String phone, Integer type, LocalDateTime now);
}    