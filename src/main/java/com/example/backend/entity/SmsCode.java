package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sms_code")
public class SmsCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String code;
    
    @Column(nullable = false)
    private Integer type;
    
    @Column(nullable = false)
    private LocalDateTime expireTime;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    public LocalDateTime getExpireTime() { return expireTime; }
    public void setExpireTime(LocalDateTime expireTime) { this.expireTime = expireTime; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}    