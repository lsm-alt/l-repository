package com.codedata.rbac.system.controller;

import com.codedata.rbac.common.group.DefaultGroup;
import com.codedata.rbac.common.utils.Result;
import com.codedata.rbac.common.utils.ValidatorUtils;
import com.codedata.rbac.security.auth.AuthedUser;
import com.codedata.rbac.security.service.ShiroService;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.system.annotation.SysLog;
import com.codedata.rbac.system.entity.UserEntity;
import com.codedata.rbac.system.enums.MenuType;
import com.codedata.rbac.system.service.MenuService;
import com.codedata.rbac.system.view.MenuView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/system/menu")
@Api(tags="菜单管理")
public class MenuController {
    private MenuService menuService;
    private ShiroService shiroService;
    public MenuController(MenuService menuService, ShiroService shiroService) {
        this.menuService = menuService;
        this.shiroService = shiroService;
    }
    @GetMapping("nav")
    @ApiOperation("导航菜单")
    public Result<List<MenuView>> nav(){
        UserEntity user = AuthedUser.getUser();
        List<MenuView> list = menuService.getUserMenuList(user, MenuType.MENU.value());
        return new Result<List<MenuView>>().ok(list);
    }
    @GetMapping("permissions")
    @ApiOperation("获取权限标识")
    public Result<Set<String>> permissions(){
        UserEntity user = AuthedUser.getUser();
        Set<String> set = shiroService.getUserPermissions(user);
        return new Result<Set<String>>().ok(set);
    }
    @GetMapping("list")
    @ApiOperation("菜单列表")
    @ApiImplicitParam(name = "type", value = "菜单类型 0：菜单 1：按钮  null：全部", paramType = "query", dataType="int")
    @RequiresPermissions("system:menu:list")
    public Result<List<MenuView>> list(Integer type){
        List<MenuView> list = menuService.getMenuByType(type);
        return new Result<List<MenuView>>().ok(list);
    }

    @GetMapping("detail/{id}")
    @ApiOperation("获取菜单详情")
    @RequiresPermissions("system:menu:detail")
    public Result<MenuView> detail(@PathVariable("id") Long id){
        MenuView data = menuService.detail(id);
        return new Result<MenuView>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存菜单信息")
    @RequiresPermissions("system:menu:save")
    @SysLog(type = "保存菜单数据")
    public Result save(@RequestBody MenuView view){
        //效验数据
        ValidatorUtils.validateEntity(view, DefaultGroup.class);
        menuService.save(view);
        return new Result();
    }

    @GetMapping("select")
    @ApiOperation("用户菜单选择列表")
    @RequiresPermissions("system:menu:select")
    public Result<List<MenuView>> select(){
        UserEntity user = AuthedUser.getUser();
        List<MenuView> list = menuService.getUserMenuList(user, null);
        return new Result<List<MenuView>>().ok(list);
    }

    @PostMapping("/update")
    @ApiOperation("更新菜单")
    @RequiresPermissions("system:menu:update")
    @SysLog(type = "更新菜单数据")
    public Result update(@RequestBody MenuView view){
        //效验数据
        ValidatorUtils.validateEntity(view, DefaultGroup.class);

        menuService.update(view);

        return new Result();
    }
    @PostMapping("delete/{id}")
    @ApiOperation("删除菜单")
    @RequiresPermissions("system:menu:delete")
    @SysLog(type = "删除菜单数据")
    public Result delete(@PathVariable("id") Long id){
        //判断是否有子菜单或按钮
        List<MenuView> list = menuService.getMenuListByPid(id);
        if(list.size() > 0){
            return new Result().error(ErrorCode.ERR_MENU,"该菜单还有子菜单没有删除请手动删除");
        }
        menuService.delete(id);
        return new Result();
    }
}
