package com.zy.graduation1.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class MD5Utils {

    public static String encode(String pwd) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5.digest(pwd.getBytes("utf-8")));
        }catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
