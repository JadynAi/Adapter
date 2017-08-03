package com.example.adapterloli.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.adapterloli.utils.function.Constants;

import java.text.NumberFormat;
import java.util.Random;

public class StringUtils {

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equalsIgnoreCase(str2);
    }

    public static String getRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(str.length());

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomStr() {
        return getRandomStr(16);
    }

    /**
     * 100,000   加逗号的格式化
     *
     * @param num
     * @return
     */
    public static String numberFormat(int num) {
        NumberFormat cFormat = NumberFormat.getCurrencyInstance();
        cFormat.setMaximumFractionDigits(0);
        return cFormat.format(num).substring(1);
    }

    public static String numberFormatF2(double num) {
        NumberFormat cFormat = NumberFormat.getCurrencyInstance();
        cFormat.setMaximumFractionDigits(2);
        return cFormat.format(num).substring(1);
    }

    public static int parseInt(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }

        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param msg         输入的待处理的字符串
     * @param lengthLimit 要求转换的字符串的最大长度
     * @return 如果输入的字符串的长度大于长度的上限，则省略中间字符并用“……”代替
     */
    public static String getSubStr(String msg, int lengthLimit) {
        if (isBlank(msg)) {
            return Constants.EMPTY;
        }
        if (msg.length() <= lengthLimit) {
            return msg;
        }
        int length = (int) Math.floor(lengthLimit / 2) - 1;
        String preStr = msg.substring(0, length);
        String lastStr = msg.substring(msg.length() - length);
        StringBuilder builder = new StringBuilder();
        builder.append(preStr).append(Constants.ELLIPSIS).append(lastStr);
        return builder.toString();
    }

    public static String getString(String str) {
        return str == null ? Constants.EMPTY : str;
    }

    public static String getString(String str, @NonNull String defStr) {
        return StringUtils.isBlank(str) ? defStr : str;
    }

    public static boolean equalsExcludeNull(CharSequence a, CharSequence b) {
        if (StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
            return false;
        }
        String a1 = a.toString().trim();
        String b1 = b.toString().trim();
        if (StringUtils.isBlank(a1) || StringUtils.isBlank(b1)) {
            return false;
        }
        return TextUtils.equals(a1, b1);
    }

    public static boolean differsExcludeNull(CharSequence a, CharSequence b) {
        if (StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
            return false;
        }
        String a1 = a.toString().trim();
        String b1 = b.toString().trim();
        if (StringUtils.isBlank(a1) || StringUtils.isBlank(b1)) {
            return false;
        }
        return !TextUtils.equals(a1, b1);
    }
}
