package cn.maidaotech.edu.sign.api.user.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-19 10:09
 **/
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String email;
    @Column
    private String mobile;
    @Column
    private String nick;
    @JSONField(serialize = false)
    @Column
    private String password;
    @Column(updatable = false)
    private Long createdAt;

    @Transient
    private String vcode;
    @Transient
    private String mobileVCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getMobileVCode() {
        return mobileVCode;
    }

    public void setMobileVCode(String mobileVCode) {
        this.mobileVCode = mobileVCode;
    }
}
