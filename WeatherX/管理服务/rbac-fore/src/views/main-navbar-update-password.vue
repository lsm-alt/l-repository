<template>
  <el-dialog
    :visible.sync="visible"
    title="修改密码"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :append-to-body="true">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmitHandle()" label-width="120px">
      <el-form-item label="账号">
        <span>{{ $store.state.user.name }}</span>
      </el-form-item>
      <el-form-item prop="password" label="原密码">
        <el-input v-model="dataForm.password" type="password" placeholder="原密码"></el-input>
      </el-form-item>
      <el-form-item prop="newPassword" label="新密码">
        <el-input v-model="dataForm.newPassword" type="password" placeholder="新密码"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword" label="确认密码">
        <el-input v-model="dataForm.confirmPassword" type="password" placeholder="确认密码"></el-input>
      </el-form-item>
    </el-form>
    <template slot="footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确认</el-button>
    </template>
  </el-dialog>
</template>

<script>
import debounce from 'lodash/debounce'
import { clearLoginInfo } from '@/utils'
export default {
  data () {
    return {
      visible: false,
      dataForm: {
        password: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  computed: {
    dataRule () {
      var validateConfirmPassword = (rule, value, callback) => {
        if (this.dataForm.newPassword !== value) {
          return callback(new Error("确认密码与新密码输入不一致"))
        }
        callback()
      }
      return {
        password: [
          { required: true, message: "原密码不能为空", trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })
    },
    // 表单提交
    dataFormSubmitHandle: debounce(function () {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) {
          return false
        }
        this.$http.post('/system/user/password', this.dataForm).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.$message({
            message: "修改密码成功",
            type: 'success',
            duration: 500,
            onClose: () => {
              this.visible = false
              clearLoginInfo()
              this.$router.replace({ name: 'login' })
            }
          })
        }).catch(() => {})
      })
    }, 1000, { 'leading': true, 'trailing': false })
  }
}
</script>
