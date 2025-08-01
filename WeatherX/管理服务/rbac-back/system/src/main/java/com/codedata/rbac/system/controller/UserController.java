package com.codedata.rbac.system.controller;

import com.codedata.rbac.common.group.AddGroup;
import com.codedata.rbac.common.group.DefaultGroup;
import com.codedata.rbac.common.group.UpdateGroup;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.utils.ConvertUtils;
import com.codedata.rbac.common.utils.Result;
import com.codedata.rbac.common.utils.ValidatorUtils;
import com.codedata.rbac.security.auth.AuthedUser;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.system.annotation.SysLog;
import com.codedata.rbac.system.service.UserRoleService;
import com.codedata.rbac.system.service.UserService;
import com.codedata.rbac.system.view.PasswordView;
import com.codedata.rbac.system.view.UserView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户管理")
@RequestMapping("/system/user")
public class UserController {
    private UserService userService;
    private UserRoleService userRoleService;
    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }
    @GetMapping("query")
    @ApiOperation("获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constants.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constants.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constants.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType="String"),
    })
    @RequiresPermissions("system:user:query")
    public Result<PageData<UserView>> query(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<UserView> page = userService.query(params);
        return new Result<PageData<UserView>>().ok(page);
    }
    @GetMapping("detail/{id}")
    @ApiOperation("获取详情")
    @RequiresPermissions("system:user:detail")
    public Result<UserView> detail(@PathVariable("id") Long id){
        UserView view = userService.detail(id);
        //用户角色列表
        List<Long> roleIdList = userRoleService.getRoleIdList(id);
        view.setRoleIdList(roleIdList);
        return new Result<UserView>().ok(view);
    }
    @PostMapping("save")
    @ApiOperation("保存数据")
    @RequiresPermissions("system:user:save")
    @SysLog(type = "保存用户数据")
    public Result save(@RequestBody UserView view){
        ValidatorUtils.validateEntity(view, AddGroup.class, DefaultGroup.class);
        userService.save(view);
        return new Result();
    }
    @PostMapping("update")
    @ApiOperation("修改数据")
    @RequiresPermissions("system:user:update")
    @SysLog(type = "更新用户数据")
    public Result update(@RequestBody UserView view){
        ValidatorUtils.validateEntity(view, UpdateGroup.class, DefaultGroup.class);
        userService.update(view);
        return new Result();
    }
    @PostMapping("delete")
    @ApiOperation("删除数据")
    @SysLog(type = "删除用户数据")
    @RequiresPermissions("system:user:delete")
    public Result delete(@RequestBody Long[] ids){
        userService.delete(ids);
        return new Result();
    }

    @GetMapping("info")
    @ApiOperation("登录用户信息")
    public Result<UserView> info(){
        UserView data = ConvertUtils.sourceToTarget(AuthedUser.getUser(), UserView.class);
        return new Result<UserView>().ok(data);
    }

    @PostMapping("password")
    @ApiOperation("修改密码")
    public Result password(@RequestBody PasswordView view){
        ValidatorUtils.validateEntity(view);
        userService.password(view);
        return new Result();
    }

    @GetMapping("select")
    @ApiOperation("获取用户下拉列表")
    @RequiresPermissions("system:user:select")
    public Result<List<UserView>> select(){
        List<UserView> userViewList = userService.select(new HashMap<String,Object>());
        return new Result<List<UserView>>().ok(userViewList);
    }

}
