package com.codedata.rbac.common.interceptor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitSystem implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
