package cn.maidaotech.edu.sign.api.support.service;

import cn.maidaotech.edu.sign.api.commons.cache.CacheOptions;
import cn.maidaotech.edu.sign.api.commons.cache.KvCacheFactory;
import cn.maidaotech.edu.sign.api.commons.cache.KvCacheWrap;
import cn.maidaotech.edu.sign.api.commons.exception.ServiceException;
import cn.maidaotech.edu.sign.api.support.model.SupportErrors;
import cn.maidaotech.edu.sign.api.support.model.VCode;
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
 * @create: 2019-06-13 10:16
 **/
@Service
public class VCodeServiceImp implements VCodeService, SupportErrors {

    @Autowired
    private KvCacheFactory kvCacheFactory;
    private KvCacheWrap<Long, VCode> vCodeCache;

    @PostConstruct
    public void init() {
        vCodeCache = kvCacheFactory.create(new CacheOptions("vcode", 1, 300),
                new RepositoryProvider<Long, VCode>() {
                    @Override
                    public VCode findByKey(Long aLong) throws Exception {
                        throw new Exception();
                    }

                    @Override
                    public Map<Long, VCode> findByKeys(Collection<Long> collection) throws Exception {
                        throw new UnsupportedOperationException("findByKeys");
                    }
                }, new BeanModelConverter<>(VCode.class));
    }

    @Override
    public void saveVCode(VCode vCode) {
        vCodeCache.save(vCode.getKey(), vCode);
    }

    @Override
    public VCode getVCode(Long key) throws Exception {
        try {
            return vCodeCache.findByKey(key);
        } catch (Exception e) {
            throw new ServiceException(ERR_VCODE_OVERTIME);
        }
    }
}
