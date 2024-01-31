package com.aurora.service;

import com.aurora.model.dto.*;
import com.aurora.model.vo.*;

import java.util.List;
import java.util.Map;

public interface UserAuthService {

    void sendCode(String username);

    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);

    void register(UserVO userVO);

    void updatePassword(UserVO userVO);

    void updateAdminPassword(PasswordVO passwordVO);

    PageResultDTO<UserAdminDTO> listUsers(ConditionVO condition);

    UserLogoutStatusDTO logout();

    UserInfoDTO qqLogin(QQLoginVO qqLoginVO);


    /**
     * 微信openId免登录接口
     * @param reqMap
     */
    UserInfoDTO weixinLogin(Map<String, Object> reqMap);
}
