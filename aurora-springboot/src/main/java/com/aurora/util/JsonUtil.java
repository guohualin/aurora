package com.aurora.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * @author My.Peng
 */
public class JsonUtil {

    /**
     * 字符串Json格式转换为对象Map
     *
     * @param strJson {"username":"lp"}
     * @return 根据json转换为Map对象
     */
    public static Map<String, Object> jsonToMap(String strJson) {
        Map<String, Object> jsoMap = new HashMap<String, Object>();
        try {
            jsoMap = JSONObject.parseObject(strJson, Map.class);
        } catch (JSONException e) {
            System.out.println("json转换Map出错：" + e.getMessage());
        }
        return jsoMap;
    }

    /**
     * 字符串Json格式转换为对象Map
     *
     * @param strJson {"username":"lp"}
     * @return 根据json转换为Map对象
     */
    public static Map<String, String> jsonToMapString(String strJson) {
        Map<String, String> jsoMap = new HashMap<String, String>();
        try {
            jsoMap = JSONObject.parseObject(strJson, Map.class);
        } catch (JSONException e) {
            System.out.println("json转换Map出错：" + e.getMessage());
        }
        return jsoMap;
    }


    /**
     * 字符串Json 转换为对象List
     *
     * @param strJson [{"username":"lp"}]
     * @return 根据json转换List
     */
    public static List<Map<String, Object>> jsonToList(String strJson) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = JSONObject.parseObject(strJson, List.class);
        } catch (JSONException e) {
            System.out.println("json转换List出错：" + e.getMessage());
        }
        return list;
    }
}

