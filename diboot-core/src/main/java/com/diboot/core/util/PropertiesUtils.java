package com.diboot.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * 配置文件工具类
 * @author Mazhicheng
 * @version v2.0
 * @date 2019/01/01
 */
public class PropertiesUtils {
    private static final Logger log = LoggerFactory.getLogger(PropertiesUtils.class);

    /**
     * Spring配置环境变量
     */
    private static Environment environment;

    /***
     *  读取配置项的值
     * @param key
     * @return
     */
    public static String get(String key){
        // 获取配置值
        if(environment == null){
            environment = ContextHelper.getApplicationContext().getEnvironment();
        }
        String value = environment.getProperty(key);
        // 任何password相关的参数需解密
        boolean isSensitiveConfig = key.contains(".password") || key.contains(".secret");
        if(value != null && isSensitiveConfig){
            value = Encryptor.decrypt(value);
        }
        return value;
    }

    /***
     *  读取int型的配置项
     * @param key
     * @return
     */
    public static Integer getInteger(String key){
        // 获取配置值
        String value = get(key);
        if(V.notEmpty(value)){
            return Integer.parseInt(value);
        }
        return null;
    }

    /***
     * 读取boolean值的配置项
     */
    public static boolean getBoolean(String key) {
        // 获取配置值
        String value = get(key);
        if(V.notEmpty(value)){
            return V.isTrue(value);
        }
        return false;
    }
}
