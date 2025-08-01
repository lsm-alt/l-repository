package com.codedata.rbac.test;

import com.codedata.rbac.service.JujubeService;
import com.codedata.rbac.utils.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenUtilsSpringTest {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private JujubeService jujubeService;
    @Test
    public void testToken() throws Exception {
        jujubeService.build();

    }
}