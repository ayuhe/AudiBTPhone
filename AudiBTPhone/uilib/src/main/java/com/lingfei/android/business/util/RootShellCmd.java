package com.lingfei.android.business.util;

import java.io.OutputStream;

/**
 * 用root权限执行Linux下的Shell指令
 * Created by heyu on 2016/7/29.
 */
public class RootShellCmd {

    private OutputStream os;

    /**
     * 执行shell指令
     *
     * @param cmd 指令
     */
    public final void exec(String cmd) {
        try {
            if (os == null) {
                os = Runtime.getRuntime().exec("su").getOutputStream();
            }
            os.write(cmd.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 后台模拟全局按键
     *
     * @param keyCode 键值
     */
    public final void simulateKey(int keyCode) {
        exec("input keyevent " + keyCode + "\n");
    }
}