r<template>
  <div class="aui-wrapper aui-page__login">
    <div class="aui-content__wrapper">
      <main class="aui-content">
        <div class="login-header">
          <h2 class="login-brand">(RBAC)权限管理系统</h2>
        </div>
        <div class="login-body">
          <h3 class="login-title">登陆</h3>
          <el-form
            :model="dataForm"
            :rules="dataRule"
            ref="dataForm"
            @keyup.enter.native="dataFormSubmitHandle()"
            status-icon
          >
            <el-form-item prop="username">
              <el-input v-model="dataForm.username" placeholder="用户名">
                <span slot="prefix" class="el-input__icon">
                  <svg class="icon-svg" aria-hidden="true">
                    <use xlink:href="#icon-user"></use>
                  </svg>
                </span>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="dataForm.password"
                type="password"
                placeholder="密码"
              >
                <span slot="prefix" class="el-input__icon">
                  <svg class="icon-svg" aria-hidden="true">
                    <use xlink:href="#icon-lock"></use>
                  </svg>
                </span>
              </el-input>
            </el-form-item>
            <el-form-item prop="captcha">
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-input v-model="dataForm.captcha" placeholder="验证码">
                    <span slot="prefix" class="el-input__icon">
                      <svg class="icon-svg" aria-hidden="true">
                        <use xlink:href="#icon-safetycertificate"></use>
                      </svg>
                    </span>
                  </el-input>
                </el-col>
                <el-col :span="10" class="login-captcha">
                  <img :src="captchaPath" @click="getCaptcha()" />
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                @click="dataFormSubmitHandle()"
                class="w-percent-100"
                >登录</el-button
              >
            </el-form-item>
          </el-form>
        </div>
        <div class="login-footer">
          <p><a target="_blank">(RBAC)权限管理系统</a>2023 &copy;</p>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import debounce from "lodash/debounce";
import { getUUID } from "@/utils";
import Constants from "@/utils/Constants";
export default {
  data() {
    return {
      captchaPath: "",
      dataForm: {
        username: "admin",
        password: "admin",
        uuid: "",
        captcha: "",
      },
    };
  },
  computed: {
    dataRule() {
      return {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
        ],
        captcha: [
          { required: true, message: "验证码不能为空", trigger: "blur" },
        ],
      };
    },
  },
  created() {
    this.getCaptcha();
  },
  methods: {
    // 获取验证码
    getCaptcha() {
      this.dataForm.uuid = getUUID();
      this.captchaPath = `${window.SITE_CONFIG["apiURL"]}/security/captcha/?uuid=${this.dataForm.uuid}`;
    },
    // 表单提交
    dataFormSubmitHandle: debounce(
      function () {
        this.$refs["dataForm"].validate((valid) => {
          if (!valid) {
            return false;
          }
          this.$http
            .post("/security/login", this.dataForm)
            .then(({ data: res }) => {
              if (res.code !== 0) {
                this.getCaptcha();
                return this.$message.error(res.msg);
              }
              //保存token
              Cookies.set(Constants.token, res.data.token);
              this.$router.replace({ name: "home" });
            })
            .catch(() => {});
        });
      },
      1000,
      { leading: true, trailing: false }
    ),
  },
};
</script>
