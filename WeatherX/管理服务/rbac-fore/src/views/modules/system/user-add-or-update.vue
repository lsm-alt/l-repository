<template>
  <el-dialog
    :visible.sync="visible"
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmitHandle()"
      label-width="120px"
    >
      <el-form-item prop="username" label="用户名">
        <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item
        prop="password"
        label="密码"
        :class="{ 'is-required': !dataForm.id }"
      >
        <el-input
          v-model="dataForm.password"
          type="password"
          placeholder="密码"
        ></el-input>
      </el-form-item>
      <el-form-item
        prop="confirmPassword"
        label="确认密码"
        :class="{ 'is-required': !dataForm.id }"
      >
        <el-input
          v-model="dataForm.confirmPassword"
          type="password"
          确认密码placeholder="确认密码"
        ></el-input>
      </el-form-item>
      <el-form-item prop="gender" label="性别">
        <el-radio-group v-model="dataForm.gender">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
          <el-radio :label="2">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="email" label="邮箱">
        <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="mobile" label="手机号">
        <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item prop="roleIdList" label="角色配置" class="role-list">
        <el-select
          v-model="dataForm.roleIdList"
          multiple
          placeholder="角色配置"
        >
          <el-option
            v-for="role in roleList"
            :key="role.id"
            :label="role.roleName"
            :value="role.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="status" label="状态" size="mini">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="0">停用</el-radio>
          <el-radio :label="1">正常</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template slot="footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确认</el-button>
    </template>
  </el-dialog>
</template>

<script>
import debounce from "lodash/debounce";
import { isEmail, isMobile } from "@/utils/validate";
export default {
  data() {
    return {
      visible: false,
      roleList: [],
      roleIdListDefault: [],
      dataForm: {
        id: "",
        username: "",
        password: "",
        confirmPassword: "",
        realName: "",
        gender: 0,
        email: "",
        mobile: "",
        roleIdList: [],
        status: 1,
      },
    };
  },
  computed: {
    dataRule() {
      var validatePassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          return callback(new Error("密码不能为空"));
        }
        callback();
      };
      var validateConfirmPassword = (rule, value, callback) => {
        if (!this.dataForm.id && !/\S/.test(value)) {
          return callback(new Error("确认密码不能为空"));
        }
        if (this.dataForm.password !== value) {
          return callback(new Error("确认密码与新密码输入不一致"));
        }
        callback();
      };
      var validateEmail = (rule, value, callback) => {
        if (value && !isEmail(value)) {
          return callback(new Error("邮箱格式不正确"));
        }
        callback();
      };
      var validateMobile = (rule, value, callback) => {
        if (value && !isMobile(value)) {
          return callback(new Error("手机号格式不正确"));
        }
        callback();
      };
      return {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
        ],
        password: [{ validator: validatePassword, trigger: "blur" }],
        confirmPassword: [
          { validator: validateConfirmPassword, trigger: "blur" },
        ],
        realName: [
          { required: true, message: "真实姓名不能为空", trigger: "blur" },
        ],
        email: [{ validator: validateEmail, trigger: "blur" }],
        mobile: [{ validator: validateMobile, trigger: "blur" }],
      };
    },
  },
  methods: {
    init() {
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        this.roleIdListDefault = [];
        Promise.all([this.getRoleList()]).then(() => {
          if (this.dataForm.id) {
            this.getDetail();
          }
        });
      });
    },
    // 获取角色列表
    getRoleList() {
      return this.$http
        .get("/system/role/select")
        .then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg);
          }
          this.roleList = res.data;
        })
        .catch(() => {});
    },
    // 获取详细信息
    getDetail() {
      this.$http
        .get(`/system/user/detail/${this.dataForm.id}`)
        .then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg);
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data,
            roleIdList: [],
          };
          // 角色配置, 区分是否为默认角色
          for (var i = 0; i < res.data.roleIdList.length; i++) {
            if (
              this.roleList.filter(
                (item) => item.id === res.data.roleIdList[i]
              )[0]
            ) {
              this.dataForm.roleIdList.push(res.data.roleIdList[i]);
              continue;
            }
            this.roleIdListDefault.push(res.data.roleIdList[i]);
          }
        })
        .catch(() => {});
    },
    // 表单提交
    dataFormSubmitHandle: debounce(
      function () {
        this.$refs["dataForm"].validate((valid) => {
          if (!valid) {
            return false;
          }
          this.$http
            .post(
              !this.dataForm.id ? "/system/user/save" : "/system/user/update",
              {
                ...this.dataForm,
                roleIdList: [
                  ...this.dataForm.roleIdList,
                  ...this.roleIdListDefault,
                ]
              }
            )
            .then(({ data: res }) => {
              if (res.code !== 0) {
                return this.$message.error(res.msg);
              }
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                },
              });
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

<style lang="scss">
.mod-system__user {
  .role-list {
    .el-select {
      width: 100%;
    }
  }
}
</style>
