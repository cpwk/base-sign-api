package cn.maidaotech.edu.sign.api.support.model;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-13 10:46
 **/
public class VCode {

    private Long key;
    private String code;

    public VCode(){}

    public VCode(Long key, String code) {
        this.key = key;
        this.code = code;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
