package cn.maidaotech.edu.sign.api.support.service;

import cn.maidaotech.edu.sign.api.support.model.VCode;

/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">河南迈道科技有限公司</a>(like)
 * @create: 2019-06-13 11:00
 **/
public interface VCodeService {
    void saveVCode(VCode vCode);

    VCode getVCode(Long key) throws Exception;
}
