package com.codedata.rbac.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codedata.rbac.common.utils.ConvertUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 用于解析 IPage<?> page 返回的分页数据
 */
@ApiModel("分页数据实体")
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("总记录数")
    private int total;
    @ApiModelProperty("列表数据")
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PageData(List<T> list, int total) {
        this.list = list;
        this.total = total;
    }
    public PageData(IPage<T> page) {
        this.list = page.getRecords();
        this.total = (int) page.getTotal();
    }

    /**
     * 转换成指定类型的list
     * @param page
     * @param clazz
     */
    public PageData(IPage<?> page,Class<T> clazz) {
        this.list =  ConvertUtils.sourceToTarget(page.getRecords(),clazz);
        this.total = (int) page.getTotal();
    }
}
