package com.aurora.strategy.context;


import com.aurora.model.dto.UserInfoDTO;
import com.aurora.enums.LoginTypeEnum;
import com.aurora.strategy.SocialLoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 其他三方登录接口
 */
@Service
public class SocialLoginStrategyContext {

    @Autowired
    private Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    /**
     * 三方登录接口
     * @param data
     * @param loginTypeEnum
     * @return
     */
    public UserInfoDTO executeLoginStrategy(String data, LoginTypeEnum loginTypeEnum) {
        return socialLoginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }

}
