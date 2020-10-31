package org.litu.app.service.impl;

import org.litu.app.service.IWebserviceTest;
import org.litu.base.vo.BaseRes;

/**
 * webservic 接口实现
 */
public class WebserviceTestImpl implements IWebserviceTest {

    @Override
    public String getName(Long userId) {
        return "liyd-" + userId;
    }

    @Override
    public BaseRes getUser(Integer userId) {
        BaseRes result = new BaseRes();
        result.put("data", "hello word");
        return result;
    }
}
