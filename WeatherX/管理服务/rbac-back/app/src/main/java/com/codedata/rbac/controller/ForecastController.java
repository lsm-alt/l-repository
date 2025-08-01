package com.codedata.rbac.controller;

import cn.hutool.core.codec.Base64;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.utils.DateUtil;
import com.codedata.rbac.common.utils.Result;
import com.codedata.rbac.entity.ForecastEntity;
import com.codedata.rbac.service.ForecastService;
import com.codedata.rbac.system.annotation.SysLog;
import com.codedata.rbac.view.ForecastView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weather/forecast")
@Api(tags="七天预报管理")
public class ForecastController {
    private final static Logger logger = LoggerFactory.getLogger(ForecastController.class);
    private ForecastService forecastService;
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }
    @PostMapping("save")
    @ApiOperation("保存七天预报信息")
    @SysLog(type = "保存七天预报信息")
    // @RequiresPermissions("system:role:select")
    public Result save(@RequestParam("rawText") String rawText,
                       @RequestParam("file") MultipartFile file) {
        if (rawText == null){
            throw new ServiceException(ErrorCode.ERR_PARAMETERS);
        }
        byte[] decode = Base64.decode(rawText);
        if (file == null){
            throw new ServiceException(ErrorCode.ERR_PARAMETERS);
        }
        forecastService.insert(new String(decode),file);
        return new Result();
    }
    @GetMapping("query")
    @ApiOperation("获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constants.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constants.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constants.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "forecastDate", value = "文件名称", paramType = "query", dataType="String"),
    })
    //@RequiresPermissions("system:user:query")
    public Result<PageData<ForecastEntity>> query(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<ForecastEntity> page = forecastService.query(params);
        return new Result<PageData<ForecastEntity>>().ok(page);
    }
    @GetMapping("getdate")
    @ApiOperation("获取系统时间")
   // @RequiresPermissions("system:role:detail")
    public Result<HashMap<String, String>> getDate(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("month", DateUtil.getNowMonth());
        hashMap.put("date", DateUtil.getNowDay());
        hashMap.put("hours", DateUtil.getHours());
        return new Result<HashMap<String, String>>().ok(hashMap);
    }
    @PostMapping("delete")
    @ApiOperation("删除七天预报数据")
    @SysLog(type = "删除七天预报数据")
    //@RequiresPermissions("system:user:delete")
    public Result delete(@RequestBody Long[] ids){
        forecastService.delete(ids);
        return new Result();
    }
    @GetMapping("detail/{id}")
    @ApiOperation("获取详情")
    //@RequiresPermissions("system:role:detail")
    public Result<ForecastView> detail(@PathVariable("id") Long id){
        ForecastView data = forecastService.detail(id);
        return new Result<ForecastView>().ok(data);
    }
    @PostMapping("update")
    @ApiOperation("更新七天预报")
    //@RequiresPermissions("system:user:update")
    @SysLog(type = "更新七天预报数据")
    public Result update(@RequestParam("id") Long id,
                         @RequestParam("rawText") String rawText,
                         @RequestParam("file") MultipartFile file){
        if (rawText == null){
            throw new ServiceException(ErrorCode.ERR_PARAMETERS);
        }
        byte[] decode = Base64.decode(rawText);
        if (file == null){
            throw new ServiceException(ErrorCode.ERR_PARAMETERS);
        }
        forecastService.update(id,new String(decode),file);
        return new Result();
    }
    @GetMapping("download/{id}")
    @ApiOperation(value = "下载七天预报PDF")
    @SysLog(type = "下载七天预报PDF")
   // @RequiresPermissions("key:trustcert:download")
    public Result download(@PathVariable("id") Long id){
        String name = forecastService.download(id);
        return new Result().ok(name);
    }

}