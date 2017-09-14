/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.zhl.huiqu.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * TODO: description
 * Date: 2016-11-29
 *
 * @author xucaigui
 */
public class SigntrueUtil {

    private static final Logger logger = Logger.getLogger("SigntrueUtil");
    /**
     * 根据入参和密钥获取签名
     *
     * @param data
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String getSignature(JSONObject data, String secretKey) throws Exception {
        //logger.info("data:" +  data);
        // 第一步：获取并排序json数据
        //忽略签名
        data.remove("sign");
        //递归获取json结构中的键值对，组合键值并保存到列表中
        List<String> keyValueList = new ArrayList<String>();
        propertyFilter(null, data, keyValueList);
        //对列表进行排序，区分大小写
        Collections.sort(keyValueList);
        Object json = JSON.toJSON(keyValueList);

        // 第二步：格式化数据，用&分割
        String formatText = StringUtils.join(keyValueList, "&");
        //在首尾加上秘钥，用&分割
        String finalText = secretKey + "&" + formatText + "&" + secretKey;

        // 第三步：MD5加密并转换成大写的16进制(finalText为utf-8编码)
        String md5 = getMD5(finalText).toUpperCase();

        return md5;
    }
    /**
     * PropertyPreFilter
     * 与jsonObjectPropertyFilter，jsonArrayPropertyFilter配合完成键值对的抽取组合
     *
     * @param value
     * @return
     */
    private static void propertyFilter(String key, Object value, List<String> list) {
        if (null == value) {
            return;
        }
        if (value instanceof JSONObject) {
            jsonObjectPropertyFilter(key, (JSONObject) value, list);
        } else if (value instanceof JSONArray) {
            jsonArrayPropertyFilter(key, (JSONArray) value, list);
        } else {
            if (value != null && value.toString().length() > 0) {
                list.add(key.trim() + "=" + value);
            }
        }
    }


    /**
     * jsonObjectPropertyFilter 过滤json对象
     *
     * @param value
     * @return
     */
    private static void jsonObjectPropertyFilter(String key, JSONObject value, List<String> list) {
        JSONObject jsonObject = value;
        if (jsonObject.isEmpty()) {
            return;
        }
        for (String str : jsonObject.keySet()) {
            propertyFilter(str, jsonObject.get(str), list);
        }
    }

    /**
     * jsonArrayPropertyFilter 过滤json数组
     *
     * @param value
     * @return
     */
    private static void jsonArrayPropertyFilter(String key, JSONArray value, List<String> list) {
        JSONArray jsonArray = value;
        if (jsonArray.isEmpty()) {
            return;
        }
        for (Object json : jsonArray) {
            propertyFilter(key, json, list);
        }
    }
    /**
     * 生成md5
     *
     * @param message
     * @return
     */
    private static String getMD5(String message) {
        String md5str = "";
        try {
            //1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            //2 将消息变成byte数组
            byte[] input = message.getBytes(Charset.forName("UTF-8"));

            //3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);

            //4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);

        } catch (Exception e) {
        }
        return md5str;
    }

    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
}
