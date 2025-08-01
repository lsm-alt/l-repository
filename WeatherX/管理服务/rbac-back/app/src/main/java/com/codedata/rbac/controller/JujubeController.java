package com.codedata.rbac.controller;

import com.codedata.rbac.common.utils.Result;
import com.codedata.rbac.service.JujubeService;
import com.codedata.rbac.system.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather/jujube")
@Api(tags="冬枣指数管理")
public class JujubeController {

    private JujubeService jujubeService;
    public JujubeController(JujubeService jujubeService) {
        this.jujubeService = jujubeService;
    }
    @GetMapping("/build")
    @ApiOperation("生成冬枣成熟期Word")
    @SysLog(type = "生成冬枣成熟期Word")
    // @RequiresPermissions("system:role:select")
    public Result build() {
        jujubeService.build();
        return new Result();
    }
}
