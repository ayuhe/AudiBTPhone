
package com.lingfei.android.uilib.util;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 和String相关的工具类
 *
 * @author yhe
 */
public class StringUtil {
    /**
     * 获取当前时间作为序列号，精确到毫秒
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeSerialNum() {
        String serial = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        serial = format.format(new Date()) + getRandomNumbers(5);

        return serial;
    }

    /**
     * @param @param str
     * @return boolean 返回类型
     * @Title: isNumeric
     * @Description: 判断是否为数字
     */
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否中文字符串
     *
     * @param str 待判断的字符串
     * @return 如果包含中文返回true,否则false
     */
    public static boolean isChinese(CharSequence str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return str.toString().matches("[\\u4e00-\\u9fa5]+");
    }

    /**
     * 是否是英文
     * @param charaString
     * @return
     */
    public static boolean isEnglish(String charaString){
        if (StringUtil.isEmpty(charaString)) {
            return false;
        }
        return charaString.matches("^[a-zA-Z]*");
    }

    /**
     * 2到10位的中英文: 只能输入中英文2~10位
     * @param charaString
     * @return
     */
    public static boolean isChineseAndEnglish(String charaString){
        if (StringUtil.isEmpty(charaString)) {
            return false;
        }
        return charaString.matches("^[a-zA-Z\\u4e00-\\u9fa5]{2,10}$");
    }

    /**
     * 数字中英文，2到10位 : 只能输入数字中英文2~20位
     * @param charaString
     * @return
     */
    public static boolean isChineseAndEnglishAndNumber(String charaString){
        if (StringUtil.isEmpty(charaString)) {
            return false;
        }
        return charaString.matches("^[0-9a-zA-Z\\u4e00-\\u9fa5]{2,10}$");
    }

    /**
     * 效验密码,A~Za~z0~9!@~#$%^&*()
     */
    public static boolean checkPassword(String charaString) {
        if (StringUtil.isEmpty(charaString)) {
            return false;
        }
        return charaString.matches("(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~`!()-=+_#@%&',;=?$\\x22]).{6,}$");
    }

    /**
     * 只能输入数字英文2~20位
     * @param charaString
     * @return
     */
    public static boolean isEnglishAndNumber(String charaString){
        return charaString.matches("^[0-9a-zA-Z]{6,20}$");
    }

    /**
     * 是否为null或空字符串
     *
     * @param str 字符串
     * @return boolean 如果是null或长度为0,则返回true,否则返回false
     */
    public static boolean isEmpty(CharSequence str) {
        return null == str || str.length() == 0;
    }

    /**
     * 是否非null或非空字符串
     *
     * @param str 字符串
     * @return boolean 如果是null或长度为0,则返回false,否则返回true
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 生成N位随机数
     *
     * @param length 随机数长度
     * @return String 生成的随机数
     */
    public static String getRandomNumbers(int length) {
        if (length <= 0) {
            return "";
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否是整数
     *
     * @param value 待判断的字符串
     * @return boolean 如果是整数格式返回true,否则返回false
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是整数
     *
     * @param value 待判断的字符串
     * @return boolean 如果是整数格式返回true,否则返回false
     */
    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     *
     * @param value 待判断的字符串
     * @return boolean 如果是浮点数格式返回true,否则返回false
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     *
     * @param value 待判断的字符串
     * @return boolean 如果是数字格式返回true,否则返回false
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 前缀补0到指定位数 如果数字的位数不足,则补0到最少位数后返回,否则直接返回
     *
     * @param inValue     需要补0的数字
     * @param inMinDigits 要求的最少位数
     * @return String 补0后的数字字符串
     */
    public static String zeroPad(int inValue, int inMinDigits) {
        return String.format(Locale.getDefault(), "%0" + inMinDigits + "d", inValue);
    }

    /**
     * 电话号码分割
     *
     * @param telNum
     * @return
     */
    public static String splitTelNum(String telNum) {
        if (telNum.length() != 11) {
            return telNum;
        }
        return telNum.substring(0, 3) + "-" + telNum.substring(3, 7) + "-"
                + telNum.substring(7, 11);
    }

    /**
     * 字符串转float
     */
    public static float getFloat(String str) {

        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Float.parseFloat(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    /**
     * 字符串转整数
     */
    public static int getInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 字符串转长整数
     */
    public static double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0d;
        }
    }

    /**
     * 去掉小数点后的数字，不要四舍五入
     * @param value
     * @return
     */
    public static String formatFloatDot(float value) {
        BigDecimal num = new BigDecimal(value);
        String str = num.toPlainString();

        if (null != str && str.contains(".")) {
            str = str.substring(0, str.indexOf("."));
        }
        return str;
    }

    /**
     * 保留两位小数
     *
     * @param str
     * @return
     */
    public static String formatDoubleDot(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format((StringUtil.getDouble(str)));
    }

    /**
     * 保留1位小数
     *
     * @param str
     * @return
     */
    public static String formatOneDot(String str) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format((StringUtil.getDouble(str)));
    }

    /**
     * 保留四位小数
     *
     * @param str
     * @return
     */
    public static String formatDoubleFourDot(String str) {
        DecimalFormat df = new DecimalFormat("0.0000");

        return df.format((StringUtil.getDouble(str)));
    }

    /**
     * 字符串转长整数
     */
    public static long getLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * 判断手机号是否正确
     *
     * @return
     */
    public static boolean isRightPhone(String phone) {
        if (StringUtil.isNotEmpty(phone) && StringUtil.isMobileNO(phone)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断验证码是否符合格式：必须是6位
     *
     * @return
     */
    public static boolean isRightAuthCode(String authCode) {
        if (StringUtil.isNotEmpty(authCode) && 6 == authCode.length()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断密码是否合格
     *
     * @return
     */
    public static boolean isRightPwd(String pwd) {
        if (StringUtil.isNotEmpty(pwd) && pwd.length() >= 6 && pwd.length() <= 20) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 数字超过3位数的，用逗号隔开
     * @param str
     * @return
     */
    public static String formatToSepara(String str) {
        if (StringUtil.isEmpty(str)) {
            return "--";
        }
        try {
            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(Double.parseDouble(str));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "--";
    }

    /**
     * 一个显示不同大小、不同颜色的字符串
     * @param tv  TextView
     * @param str  显示的字符串
     * @param sizeStartStr 开始放大字体大小
     * @param colorStartStr 开始改变字体颜色
     */
    public static void setShowSpanStr(TextView tv, String str, String sizeStartStr, String colorStartStr) {
        SpannableString msp = null;
        msp = new SpannableString(str);
        msp.setSpan(new RelativeSizeSpan(1.5f), sizeStartStr.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // x.xf表示默认字体大小的倍数
        msp.setSpan(new ForegroundColorSpan(Color.RED), colorStartStr.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  // 设置前景色
        tv.setText(msp);
    }

    @NonNull
    public static String getUserNameString(String userName) {
        if (isChinese(userName)) {
            if (userName.length() > 5) {
                String firstName = userName.substring(0, 2); // 截取前两个字
                String endName = userName.substring(userName.length() - 1, userName.length());// 截取后一个字
                userName = firstName + "..." + endName;
            }
        } else {
            if (userName.length() > 8) {
                String firstName = userName.substring(0, 5); // 截取前两个字
                String endName = userName.substring(userName.length() - 3, userName.length());// 截取后一个字
                userName = firstName + "..." + endName;
            }
        }
        return userName;
    }

    /**
     * 正则 去掉字符串中的换行、回车
     */
    public static String replaceEnter(String str) {
        String dest = "";
        if (isNotEmpty(str)) {
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
    }
}
