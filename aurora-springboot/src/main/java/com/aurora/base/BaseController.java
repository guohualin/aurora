package com.aurora.base;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class BaseController {
	private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 公共获取参数方法
     *
     * @param request
     * @return
     */
    protected Map<String, Object> getParams(HttpServletRequest request) {
        Map<String, Object> reqParams = new HashMap<String, Object>();
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String paramName = en.nextElement().toString();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                //去两边空格，（下面数组的trim，懒得写了，以后有时间再写吧）
                reqParams.put(paramName, StringUtils.trimToEmpty(paramValue));
            } else {
                //数组的trim，还要for循环，懒得写了，以后有时间再写吧
                reqParams.put(paramName, paramValues);
            }
        }
        return reqParams;
    }
}
