package com.codedata.rbac.common.utils;
import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.common.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
@ApiModel(value = "响应结构体")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码：0表示成功，其他值表示失败")
    private Integer code = ErrorCode.OK;
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String msg = "";
    /**
     * 响应状态
     */
    @ApiModelProperty(value = "响应状态")
    private String status = Constants.SUCCESS;
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public boolean success() {
        return code == ErrorCode.OK;
    }

    public Result<T> error() {
        error(ErrorCode.ERR_UNKNOWN);
        return this;
    }

    public Result<T> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.status = Constants.FAIL;
        return this;
    }

    public Result<T> error(Integer code) {
        error(code, ErrorCode.get(code));
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
