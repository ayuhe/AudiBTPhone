package com.lingfei.android.uilib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件config.properties配置属性，并初始化
 *
 * @author yhe
 */
public class Config {

    public static String REQUEST_URL; // 普通接口调的url

    /**
     * HTTP请求超时时间
     */
    public static int HTTP_TIMEOUT;

    /**
     * 与MCU通信的波特率
     */
    public static int BAUD_RATE;

				/**
     * 安卓导航板：605  、 607
     */
    public static int ANROID_BOARD;

    /**
     * 升级地址
     */
    public static String UPGRADE_URL;

    /**
     * 是否demo
     */
    public static boolean IS_DEMO;

    /**
     * 是否调试
     */
    public static boolean IS_DEBUG;

    public static Properties PROPERTIES;

    public static void init(InputStream is) {
        PROPERTIES = new Properties();

        try {
            PROPERTIES.load(is);
            REQUEST_URL = getProperty("REQUEST_URL");
            HTTP_TIMEOUT = getPropertyInteger("HTTP_TIMEOUT");
            BAUD_RATE = getPropertyInteger("BAUD_RATE");
            ANROID_BOARD = getPropertyInteger("ANROID_BOARD");
            UPGRADE_URL = getProperty("UPGRADE_URL");
            IS_DEMO = getPropertyBoolean("isDemo");
            IS_DEBUG = getPropertyBoolean("isDebug");

        } catch (IOException e) {
            throw new IllegalArgumentException(e.toString());
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e.toString());
            }
        }
    }

    private static String getProperty(String name) {
        if (!PROPERTIES.containsKey(name)) {
            throw new IllegalArgumentException("请在配置文件config.properties完成配置属性：" + name);
        }

        return PROPERTIES.getProperty(name);
    }

    private static Integer getPropertyInteger(String name) {
        if (!PROPERTIES.containsKey(name)) {
            throw new IllegalArgumentException("请在配置文件config.properties完成配置属性：" + name);
        }

        return Integer.parseInt(PROPERTIES.getProperty(name));
    }

    private static boolean getPropertyBoolean(String name) {
        if (!PROPERTIES.containsKey(name)) {
            throw new IllegalArgumentException("请在配置文件config.properties完成配置属性：" + name);
        }

        return Boolean.parseBoolean(PROPERTIES.getProperty(name));
    }
}
