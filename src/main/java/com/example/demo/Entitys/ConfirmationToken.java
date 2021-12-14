package com.example.demo.Entitys;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class ConfirmationToken {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String token;
    
    @Column(nullable = false)
    private LocalDateTime createDate;
    
    @Column(nullable = false)
    private LocalDateTime expiresDate;
    
    private LocalDateTime comfirmDate;
    
    @ManyToOne
    private User user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String token,
            LocalDateTime createDate, 
            LocalDateTime expiresDate, 
            User user) {
        this.token = token;
        this.createDate = createDate;
        this.expiresDate = expiresDate;
        this.user = user;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(LocalDateTime expiresDate) {
        this.expiresDate = expiresDate;
    }

    public LocalDateTime getComfirmDate() {
        return comfirmDate;
    }

    public void setComfirmDate(LocalDateTime comfirmDate) {
        this.comfirmDate = comfirmDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
