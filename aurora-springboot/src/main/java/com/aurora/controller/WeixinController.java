package com.aurora.controller;

import com.alibaba.fastjson.JSONObject;
import com.aurora.base.BaseController;
import com.aurora.enums.StatusCodeEnum;
import com.aurora.model.dto.UserInfoDTO;
import com.aurora.model.vo.ResultVO;
import com.aurora.service.RedisService;
import com.aurora.service.UserAuthService;
import com.aurora.strategy.impl.AbstractUploadStrategyImpl;
import com.aurora.util.BasicCommonConstant;
import com.aurora.util.BasicObjectUtils;
import com.aurora.util.JsonUtil;
import com.aurora.util.WeChatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags = "微信获取公众号验证码")
@RestController
public class WeixinController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AbstractUploadStrategyImpl.class);

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private RedisService redisService;
    @GetMapping("/weixin/test")
    public void checkToken(HttpServletRequest request, HttpServletResponse response) {
        //token验证代码段
        try {
            logger.info("请求已到达，开始校验token");
            if (StringUtils.isNotBlank(request.getParameter("signature"))) {
                String signature = request.getParameter("signature");
                String timestamp = request.getParameter("timestamp");
                String nonce = request.getParameter("nonce");
                String echostr = request.getParameter("echostr");
                logger.info("signature[{}], timestamp[{}], nonce[{}], echostr[{}]", signature, timestamp, nonce, echostr);
                if (WeChatUtil.checkSignature(signature, timestamp, nonce)) {
                    logger.info("数据源为微信后台，将echostr[{}]返回！", echostr);
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write(echostr.getBytes());
                    out.flush();
                    out.close();
                }
            }
        } catch (Exception e) {
            logger.error("校验出错",e);
        }
    }

    @ApiOperation("处理微信服务器的消息转发")
    @PostMapping(value = "/weixin/test")
    public String  wechat(HttpServletRequest request) throws Exception {
        // 调用parseXml方法解析请求消息
        Map<String,String> requestMap = WeChatUtil.parseXml(request);
        // 消息类型
        String msgType = requestMap.get("MsgType");
        String fromUserName = requestMap.get("FromUserName");//针对此公众号的用户唯一id
        logger.info("处理微信服务器的消息转发---requestMap[{}]", requestMap);
        // xml格式的消息数据
        String respXml = null;
        String mes = requestMap.get("Content");
        // 文本消息
        if ("text".equals(msgType) && "验证码".equals(mes)) {
            String code = WeChatUtil.createCode(4);
            String msg = "您的验证码为:"+code+"\n有效期300秒，如过期或验证失败可以重新发送“验证码”获取验证码";
            respXml=WeChatUtil.sendTextMsg(requestMap,msg);
            redisService.set(BasicCommonConstant.WECHAT_CODE+code,fromUserName,30L,TimeUnit.MINUTES);
        }
        return respXml;
    }

    /**
     * 微信验证码登录
     * @param request
     * @return
     */
    @ApiOperation("验证码请求校验")
    @PostMapping(value = "/weixin/callBack")
    public ResultVO<?> callBack(HttpServletRequest request) {

        Map<String, Object> params = getParams(request);

        String code = (String) params.get("code");

        //从redis中获取code是否存在
        String codeKey = BasicCommonConstant.WECHAT_CODE+code;
        String fromUserName = (String)redisService.get(codeKey);

        //判断验证码是否存在
        if(BasicObjectUtils.isNullOrEmpty(fromUserName)){

            //验证码不存在直接返回失败
            return ResultVO.fail(StatusCodeEnum.WEIXIN_LOGIN_ERROR);
        }

        //验证码存在，调用用户登录接口
        params.put("openId",fromUserName);
        UserInfoDTO userInfoDTO = userAuthService.weixinLogin(params);

        return ResultVO.ok(userInfoDTO);
    }



    /**********************微信登录请求二维码************************/
    @GetMapping(value = "/wechat/login")
    @SneakyThrows
    public void wechatLoginPage(HttpServletResponse response, @RequestParam("ticket") String ticket) {
        logger.info("请求二维码{}", ticket);
        String url = String.format(BasicCommonConstant.WECHAT_CODE, ticket);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse resp = httpclient.execute(httpGet);
        byte[] data = EntityUtils.toByteArray(resp.getEntity());
        response.getOutputStream().write(data);
    }

    /**
     * 获取ticket
     *
     * @return ticket
     */
    @PostMapping("/wechat/ticket")
    @SneakyThrows
    @ResponseBody
    public ResultVO<String> getTicket() {
        String accessToken = this.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("expire_seconds", "120");
        hashMap.put("action_name", "QR_STR_SCENE");
        hashMap.put("action_info", new JSONObject()
                .put("scene", uuid));

        JSONObject jsonObject = new JSONObject(hashMap);
        String param = jsonObject.toString();
        httpPost.setEntity(new StringEntity(param, "utf-8"));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String body = EntityUtils.toString(response.getEntity(), "utf-8");
        Map<String, String> stringStringMap = JsonUtil.jsonToMapString(body);
        logger.info("调用微信接口回显数据---[{}]",stringStringMap);
        return ResultVO.ok(stringStringMap.get("ticket"));
    }

    /**
     * 获取Access token
     *
     * @return access_token
     */
    @SneakyThrows
    public String getAccessToken() {
        String accessToken = (String) redisService.get(BasicCommonConstant.ACCESS_TOKEN);
        if(!StringUtils.isNotBlank(accessToken)){
            String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

            String url = String.format(getTokenUrl, "appid", "secret");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpGet);

            String body = EntityUtils.toString(response.getEntity(), "utf-8");
            Map<String, String> stringStringMap = JsonUtil.jsonToMapString(body);
            if (stringStringMap.get("errmsg") != null) {
                logger.error(stringStringMap.get("errmsg"));
            }
            accessToken = stringStringMap.get("access_token");
            redisService.set(BasicCommonConstant.ACCESS_TOKEN,accessToken,60,TimeUnit.MINUTES);
        }
        return accessToken;
    }
}
