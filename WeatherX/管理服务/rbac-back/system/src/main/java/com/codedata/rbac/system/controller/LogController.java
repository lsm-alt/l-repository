package com.codedata.rbac.system.controller;

import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.utils.Result;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.system.service.LogService;
import com.codedata.rbac.system.view.LogView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/system/log")
@Api(tags = "日志管理")
public class LogController {
    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("query")
    @ApiOperation("获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constants.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constants.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constants.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "result", value = "执行结果", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "dateStart", value = "开始时间 格式:" + Constants.DATE_TIME_PATTERN, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "dateEnd", value = "截止时间 格式:" + Constants.DATE_TIME_PATTERN, paramType = "query", dataType = "String"),
    })
    @RequiresPermissions("system:log:query")
    public Result<PageData<LogView>> query(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<LogView> page = logService.query(params);
        return new Result<PageData<LogView>>().ok(page);
    }

    @GetMapping("detail/{id}")
    @ApiOperation("获取详情")
    @RequiresPermissions("system:log:detail")
    public Result<LogView> detail(@PathVariable("id") Long id) {
        LogView data = logService.detail(id);
        return new Result<LogView>().ok(data);
    }
}
