package cn.maidaotech.edu.sign.api.support.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-13 12:27
 **/
@ConfigurationProperties(prefix = "sms", ignoreUnknownFields = false)
@Component
@Validated
public class SmsConfig {
    private String accessKeyId;
    private String accessKeySecret;
    private String domain;
    private String version;
    private String signName;
    private String vcodeTemplateCode;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVcodeTemplateCode() {
        return vcodeTemplateCode;
    }

    public void setVcodeTemplateCode(String vcodeTemplateCode) {
        this.vcodeTemplateCode = vcodeTemplateCode;
    }
}
