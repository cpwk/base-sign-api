package cn.maidaotech.edu.sign.api.user.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-25 14:39
 **/
@ConfigurationProperties(prefix = "usr", ignoreUnknownFields = false)
@Component
@Validated
public class UserConfig {
    private String passwordSalt;
    private Integer sessionDays;

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Integer getSessionDays() {
        return sessionDays;
    }

    public void setSessionDays(Integer sessionDays) {
        this.sessionDays = sessionDays;
    }
}
