package com.aurora.service;

import com.aurora.model.dto.AboutDTO;
import com.aurora.model.dto.AuroraAdminInfoDTO;
import com.aurora.model.dto.AuroraHomeInfoDTO;
import com.aurora.model.dto.WebsiteConfigDTO;
import com.aurora.model.vo.AboutVO;
import com.aurora.model.vo.WebsiteConfigVO;

public interface AuroraInfoService {

    void report();

    /**
     * 登录时调用获取初始化信息
     * @return
     */
    AuroraHomeInfoDTO getAuroraHomeInfo();

    AuroraAdminInfoDTO getAuroraAdminInfo();

    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    WebsiteConfigDTO getWebsiteConfig();

    void updateAbout(AboutVO aboutVO);

    AboutDTO getAbout();

}
