package cn.maidaotech.edu.sign.api.commons.resources;

import cn.maidaotech.edu.sign.api.commons.util.L;
import cn.maidaotech.edu.sign.api.commons.util.R;
import cn.maidaotech.edu.sign.api.commons.util.StringUtils;
import com.sunnysuperman.commons.locale.LocaleBundle;
import com.sunnysuperman.commons.locale.LocaleBundle.LocaleBundleOptions;
import com.sunnysuperman.commons.util.FileUtil;
import com.sunnysuperman.commons.util.StringUtil;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

@StaticInit
public class LocaleBundles {
    private static class SimpleLocaleBundle extends LocaleBundle {

        public SimpleLocaleBundle(LocaleBundleOptions options) {
            super(options);
        }

        @Override
        protected void put(String key, String locale, String value) throws Exception {
            super.put(key, locale, value);
        }

        @Override
        protected void finishPut() {
            super.finishPut();
        }

    }

    private static String DEFAULT_LOCALE;
    private static SimpleLocaleBundle BUNDLE = null;

    static {
        try {
            String[] locales = StringUtil.splitAsArray(R.getString("locales"), ",");
            L.warn("Locales: " + StringUtil.join(locales, ","));
            DEFAULT_LOCALE = locales[0];

            LocaleBundleOptions options = new LocaleBundleOptions();
            options.setDefaultLocale(DEFAULT_LOCALE);
            options.setPrefLocales(locales);
            options.setCompileStartToken("{");
            options.setCompileEndToken("}");
            BUNDLE = new SimpleLocaleBundle(options);
            for (String local : locales) {
                local = local.trim();
                Map<String, String> props = FileUtil.readProperties(R.getStream("locale/" + local+".ini"),
                        StringUtil.UTF8, false);
                for (Entry<String, String> entry : props.entrySet()) {
                    String key = StringUtils.trimToNull(entry.getKey());
                    String value = StringUtils.trimToNull(entry.getValue());
                    if (key == null || value == null) {
                        continue;
                    }
                    BUNDLE.put(key, local, value);
                }
            }
            BUNDLE.finishPut();
        } catch (Exception ex) {
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            }
            throw new UndeclaredThrowableException(ex);
        }
    }

    public static String getDefaultLocale() {
        return DEFAULT_LOCALE;
    }

    public static boolean containsKey(String key) {
        return BUNDLE.containsKey(key);
    }

    public static String get(String locale, String key) {
        return BUNDLE.getWithParams(locale, key, null);
    }

    public static String getWithParams(String locale, String key, Map<String, Object> context) {
        if (context == null) {
            context = Collections.emptyMap();
        }
        return BUNDLE.getWithParams(locale, key, context);
    }

    public static String getWithArrayParams(String locale, String key, Object[] params) {
        return BUNDLE.getWithArrayParams(locale, key, params);
    }

}
