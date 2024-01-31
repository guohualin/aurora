package com.aurora.util;

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

public class WeChatUtil {
    private static Logger logger = LoggerFactory.getLogger(WeChatUtil.class);

    private static String token = "75891494319970920guohualin";


    /**
     * 校验签名
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 布尔值
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String checktext = null;
        if (null != signature) {
            //对ToKen,timestamp,nonce 按字典排序
            String[] paramArr = new String[]{token,timestamp,nonce};
            Arrays.sort(paramArr);
            //将排序后的结果拼成一个字符串
            String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                //对接后的字符串进行sha1加密
                byte[] digest = md.digest(content.getBytes());
                checktext = byteToStr(digest);
            } catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        }
        //将加密后的字符串与signature进行对比
        return checktext !=null ? checktext.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转化我16进制字符串
     * @param byteArrays 字符数组
     * @return 字符串
     */
    private static String byteToStr(byte[] byteArrays){
        String str = "";
        for (int i = 0; i < byteArrays.length; i++) {
            str += byteToHexStr(byteArrays[i]);
        }
        return str;
    }

    /**
     *  将字节转化为十六进制字符串
     * @param myByte 字节
     * @return 字符串
     */
    private static String byteToHexStr(byte myByte) {
        char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tampArr = new char[2];
        tampArr[0] = Digit[(myByte >>> 4) & 0X0F];
        tampArr[1] = Digit[myByte & 0X0F];
        String str = new String(tampArr);
        return str;
    }

    /**
     * 解析微信发来的请求(xml)
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
        logger.info("请求已到达，开始解析参数...");

        // 将解析结果存储在HashMap中
        Map<String,String> map = new HashMap<>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        // 释放资源
        inputStream.close();
        logger.info("参数解析完成。{}",map);
        return map;
    }
    /**
     * 回复文本消息
     * @param requestMap
     * @param content
     * @return
     */
    public static String sendTextMsg(Map<String,String> requestMap,String content){

        Map<String,Object> map= new HashMap<>();
        map.put("ToUserName", requestMap.get("FromUserName"));
        map.put("FromUserName",  requestMap.get("ToUserName"));
        map.put("MsgType", "text");
        map.put("CreateTime", new Date().getTime());
        map.put("Content", content);
        return  mapToXML(map);
    }
    public static String mapToXML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        mapToXML2(map, sb);
        sb.append("</xml>");
        logger.info("请求完成,返回参数：{}",sb);
        try {
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
    }
    private static void mapToXML2(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXML2(hm, sb);
                }
                sb.append("</" + key + ">");

            } else {
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXML2((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + "><![CDATA[" + value + "]]></" + key + ">");
                }

            }

        }
    }

    /**
     * 获取随机n位自然数+字母
     * @param n
     * @return
     */
    public static String createCode(int n){
        //需要定义一个字符串来接随机字符
        String code = "";
        Random rs = new Random();
        //2定义一个for循环来生成n个字符
        for (int i = 0; i < n ; i++) {
            //3生成一个随机字符大写小写数字 0 1 2
            int type = rs.nextInt(3);
            switch (type){
                case 0:   //代表大写字母（A 65 Z65+25）
                    char ch = (char)( rs.nextInt(26) + 65);
                    code += ch;
                    break;
                case 1:  //代表小写字母 a97 z97+25
                    char ch1 = (char)( rs.nextInt(26) + 97);
                    code += ch1;
                    break;
                case 2: //代表数字
                    int a = rs.nextInt(10);
                    code += a;
                    break;
            }
        }
        return code;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

}
