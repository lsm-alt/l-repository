package com.codedata.rbac.system.controller;

import com.codedata.rbac.common.group.AddGroup;
import com.codedata.rbac.common.group.DefaultGroup;
import com.codedata.rbac.common.group.UpdateGroup;
import com.codedata.rbac.common.page.PageData;
import com.codedata.rbac.common.utils.Result;
import com.codedata.rbac.common.utils.ValidatorUtils;
import com.codedata.rbac.system.annotation.SysLog;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.system.service.RoleMenuService;
import com.codedata.rbac.system.service.RoleService;
import com.codedata.rbac.system.view.RoleView;
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
@RequestMapping("/system/role")
@Api(tags="角色管理")
public class RoleController {
    private RoleService roleService;
    private RoleMenuService roleMenuService;
    public RoleController(RoleService roleService,RoleMenuService roleMenuService) {
        this.roleService = roleService;
        this.roleMenuService = roleMenuService;
    }
    @GetMapping("query")
    @ApiOperation("获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constants.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constants.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constants.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = "name", value = "角色名", paramType = "query", dataType="String")
    })
    @RequiresPermissions("system:role:query")
    public Result<PageData<RoleView>> query(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<RoleView> page = roleService.query(params);
        return new Result<PageData<RoleView>>().ok(page);
    }

    @GetMapping("select")
    @ApiOperation("选择列表")
    @RequiresPermissions("system:role:select")
    public Result<List<RoleView>> select(){
        List<RoleView> data = roleService.select(new HashMap<String,Object>(1));
        return new Result<List<RoleView>>().ok(data);
    }

    @GetMapping("detail/{id}")
    @ApiOperation("获取详情")
    @RequiresPermissions("system:role:detail")
    public Result<RoleView> detail(@PathVariable("id") Long id){
        RoleView data = roleService.detail(id);
        //查询角色对应的菜单
        List<Long> menuIdList = roleMenuService.getMenuIdListByRoleId(id);
        data.setMenuIdList(menuIdList);
        return new Result<RoleView>().ok(data);
    }

    @PostMapping("save")
    @ApiOperation("保存数据")
    @RequiresPermissions("system:role:save")
    @SysLog(type = "保存角色数据")
    public Result save(@RequestBody RoleView view){
        //验证数据
        ValidatorUtils.validateEntity(view, AddGroup.class, DefaultGroup.class);
        roleService.save(view);
        return new Result();
    }

    @PostMapping("update")
    @ApiOperation("修改数据")
    @RequiresPermissions("system:role:update")
    @SysLog(type = "修改角色数据")
    public Result update(@RequestBody RoleView view){
        //验证数据
        ValidatorUtils.validateEntity(view, UpdateGroup.class, DefaultGroup.class);
        roleService.update(view);
        return new Result();
    }

    @PostMapping("delete")
    @ApiOperation("删除数据")
    @SysLog(type = "删除角色数据")
    @RequiresPermissions("system:role:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        if(ids==null||ids.length==0){
            throw new ServiceException(ErrorCode.ERR_PARAMETERS);
        }
        roleService.delete(ids);
        return new Result();
    }
}
