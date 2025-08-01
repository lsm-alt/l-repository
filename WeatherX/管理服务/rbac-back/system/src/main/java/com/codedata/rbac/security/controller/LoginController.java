package com.codedata.rbac.security.controller;

import com.codedata.rbac.common.constant.Constants;
import com.codedata.rbac.common.exception.ErrorCode;
import com.codedata.rbac.common.exception.ServiceException;
import com.codedata.rbac.common.utils.Result;
import com.codedata.rbac.common.utils.ValidatorUtils;
import com.codedata.rbac.security.auth.AuthedUser;
import com.codedata.rbac.security.entity.TokenEntity;
import com.codedata.rbac.security.service.CaptchaService;
import com.codedata.rbac.security.service.TokenService;
import com.codedata.rbac.security.view.LoginView;
import com.codedata.rbac.system.entity.UserEntity;
import com.codedata.rbac.system.enums.UserStatus;
import com.codedata.rbac.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "登陆管理接口")
@RequestMapping("/security")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private CaptchaService captchaService;
    private UserService userService;
    private TokenService tokenService;

    public LoginController(CaptchaService captchaService,UserService userService,TokenService tokenService) {
        this.captchaService = captchaService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("captcha")
    @ApiOperation(value = "验证码", produces="application/octet-stream")
    @ApiImplicitParam(paramType = "query", dataType="string", name = "uuid", required = true)
    public void captcha(HttpServletResponse response, String uuid){
       if(StringUtils.isBlank(uuid)){
           throw new ServiceException(ErrorCode.ERR_PARAMETERS,"验证码请求id不能为空");
       }
        //生成验证码
        captchaService.create(response, uuid);
    }

    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result login(@RequestBody LoginView login) {
        //校验数据正确性
        ValidatorUtils.validateEntity(login);
        //验证码是否正确
        boolean flag = captchaService.validate(login.getUuid(), login.getCaptcha());
        if(!flag){
            return new Result().error(ErrorCode.ERR_CAPTCHA);
        }
        UserEntity user = userService.getUserByName(login.getUsername());
        if(user == null){
            throw new ServiceException(ErrorCode.ERR_AUTH);
        }
        if(!login.getPassword().equals(user.getPassword())){
            throw new ServiceException(ErrorCode.ERR_AUTH);
        }
        if(user.getStatus()== UserStatus.DISABLE.value()){
            throw new ServiceException(ErrorCode.ERR_ACCOUNT_DISABLE);
        }
        //创建一条TOKEN
        TokenEntity token = tokenService.createToken(user.getId());
        Map<String, Object> map = new HashMap<>(2);
        map.put(Constants.TOKEN_HEADER, token.getToken());
        map.put("expire",  token.getExpireDate().getTime() - System.currentTimeMillis());
        return new Result().ok(map);
    }

    @PostMapping("logout")
    @ApiOperation(value = "登出")
    public Result logout(){
        UserEntity userEntity = AuthedUser.getUser();
        tokenService.expireToken(userEntity.getId());//销毁token
        return new Result();
    }
    @GetMapping("/getfile")
    public void relDownload(HttpServletResponse response, String filename) {

        logger.info("下载路径：" + filename);
        if (StringUtils.isBlank(filename)) return;
        File file = new File(filename);
        if (!file.exists()) return;
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        OutputStream os = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024];
            os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            logger.debug("下载文件失败："+e.getMessage(),e);
            throw new ServiceException("下载文件异常");
        } finally {
            try {
                if (os != null)
                    os.close();
                if (bis != null)
                    bis.close();
            } catch (Exception e) {
            }
            File targetFile = new File(filename);
            if (targetFile.isFile() && targetFile.exists()) {
                file.delete();
            }
        }
    }

}
