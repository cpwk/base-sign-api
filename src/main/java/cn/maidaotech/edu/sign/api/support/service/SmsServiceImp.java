package cn.maidaotech.edu.sign.api.support.service;

import cn.maidaotech.edu.sign.api.commons.cache.CacheOptions;
import cn.maidaotech.edu.sign.api.commons.cache.KvCacheFactory;
import cn.maidaotech.edu.sign.api.commons.cache.KvCacheWrap;
import cn.maidaotech.edu.sign.api.commons.exception.ServiceException;
import cn.maidaotech.edu.sign.api.commons.task.ApiTask;
import cn.maidaotech.edu.sign.api.commons.task.TaskService;
import cn.maidaotech.edu.sign.api.commons.util.L;
import cn.maidaotech.edu.sign.api.commons.util.StringUtils;
import cn.maidaotech.edu.sign.api.support.model.SmsConfig;
import cn.maidaotech.edu.sign.api.support.model.SupportErrors;
import cn.maidaotech.edu.sign.api.support.model.VCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;


/**
 * @program: sign-api
 * @description:
 * @author: <a href="http://edu.maidaotech.cn/">迈道教育</a>(ilike)
 * @create: 2019-06-13 12:13
 **/
@Service
public class SmsServiceImp implements SmsService, SupportErrors {

    private static final int VCODE_LENGTH = 6;

    @Autowired
    private SmsConfig smsConfig;
    @Autowired
    private TaskService taskService;
    @Autowired
    private KvCacheFactory kvCacheFactory;
    private KvCacheWrap<Long, VCode> vCodeCache;

    @PostConstruct
    public void init() {
        vCodeCache = kvCacheFactory.create(new CacheOptions("m_code", 1, 300),
                new RepositoryProvider<Long, VCode>() {
                    @Override
                    public VCode findByKey(Long mobile) throws Exception {
                        throw new ServiceException(ERR_MOBILE_VCODE_OVERTIME);
                    }

                    @Override
                    public Map<Long, VCode> findByKeys(Collection<Long> mobiles) throws Exception {
                        throw new UnsupportedOperationException("findByKeys");
                    }
                }, new BeanModelConverter<>(VCode.class));
    }

    @Override
    public VCode getVcode(Long key) throws Exception {
        try {
            return vCodeCache.findByKey(key);
        } catch (Exception e) {
            throw new ServiceException(ERR_MOBILE_VCODE_OVERTIME);
        }
    }

    @Override
    public void sendVcode(Long key, String mobile) throws Exception {
        if (!StringUtils.isChinaMobile(mobile)) {
            throw new ServiceException(ERR_MOBILE_INVALID);
        }
        String code = StringUtils.randomNumericString(VCODE_LENGTH);
        VCode vcode = new VCode(key, code);
        taskService.addTask(new SendMobileVcodeTask(mobile, vcode));
    }

    private class SendMobileVcodeTask extends ApiTask {
        private String mobile;
        private VCode vcode;

        public SendMobileVcodeTask(String mobile, VCode vcode) {
            this.mobile = mobile;
            this.vcode = vcode;
        }

        @Override
        protected void doApiWork() throws Exception {
            JSONObject param = new JSONObject();
            param.put("code", vcode.getCode());
            if (sendSms(mobile, smsConfig.getVcodeTemplateCode(), param)) {
                vCodeCache.save(vcode.getKey(), vcode);
            }
        }
    }

    private boolean sendSms(String mobile, String templateCode, JSONObject param) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKeyId(), smsConfig
                .getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(smsConfig.getDomain());
        request.setVersion(smsConfig.getVersion());
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject data = JSON.parseObject(response.getData());
            if ("OK".equals(data.getString("Message"))) {
                L.info("Send sms of vcode.mobile:" + mobile + ",vcode:" + param.getString("code"));
                return true;
            } else {
                L.error(response.getData());
                return false;
            }
        } catch (ServerException e) {
            throw new ServiceException(ERR_DETAILED_MESSAGE, e.getMessage());
        } catch (ClientException e) {
            throw new ServiceException(ERR_ALIYUN_EXCEPTION);
        }
    }
}
