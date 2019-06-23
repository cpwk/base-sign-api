package cn.maidaotech.edu.sign.api.commons.resources;

import cn.maidaotech.edu.sign.api.Application;
import cn.maidaotech.edu.sign.api.commons.util.L;
import cn.maidaotech.edu.sign.api.commons.util.ProcessUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class StaticBootstrap {

    @PostConstruct
    public void init() {
        Reflections reflections = Application.getAppReflection();
        {
            Set<Class<?>> staticInitClasses = reflections.getTypesAnnotatedWith(StaticInit.class);
            for (Class<?> clazz : staticInitClasses) {
                try {
                    if (L.isInfoEnabled()) {
                        L.info("[StaticBootstrap] calling: " + clazz.getCanonicalName());
                    }
                    Class.forName(clazz.getCanonicalName());
                } catch (Throwable t) {
                    ProcessUtils.exitWithMessage(null, t);
                }
            }
        }
        if (L.isInfoEnabled()) {
            L.info("[StaticBootstrap] done");
        }
    }

}
