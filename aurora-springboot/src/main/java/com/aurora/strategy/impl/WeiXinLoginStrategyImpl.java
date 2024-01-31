package com.aurora.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.aurora.constant.CommonConstant;
import com.aurora.enums.LoginTypeEnum;
import com.aurora.model.dto.SocialTokenDTO;
import com.aurora.model.dto.SocialUserInfoDTO;
import com.aurora.service.AuroraInfoService;
import com.aurora.util.BasicCommonConstant;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("weixinLoginStrategyImpl")
public class WeiXinLoginStrategyImpl extends AbstractSocialLoginStrategyImpl {

    @Autowired
    private AuroraInfoService auroraInfoService;

    @Override
    public SocialTokenDTO getSocialToken(String data) {
        Map<String,Object> weixin = JSON.parseObject(data, Map.class);
        String openId = (String) weixin.get("openId");
        return SocialTokenDTO.builder()
                .openId(openId)
                .accessToken(BasicCommonConstant.DEF_P)
                .loginType(LoginTypeEnum.WEIXIN.getType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) {
        String nickname = CommonConstant.DEFAULT_NICKNAME + IdWorker.getId();
        String userAvatar = auroraInfoService.getWebsiteConfig().getUserAvatar();
        return SocialUserInfoDTO.builder()
                .nickname(nickname)
                .avatar(userAvatar)
                .build();
    }
}
