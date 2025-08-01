<template>
  <el-dialog :visible.sync="visible" :title="!dataForm.id ? '添加' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmitHandle()" label-width="120px">
      <el-form-item prop="roleName" label="角色名称">
        <el-input v-model="dataForm.roleName" placeholder="角色名称"></el-input>
      </el-form-item>
      <el-form-item prop="bak" label="备注">
        <el-input v-model="dataForm.bak" placeholder="备注"></el-input>
      </el-form-item>
      <el-row>
          <el-form-item size="mini" label="菜单授权">
            <el-tree
              :data="menuList"
              :props="{ label: 'name', children: 'children' }"
              node-key="id"
              ref="menuListTree"
              accordion
              show-checkbox>
            </el-tree>
          </el-form-item>
      </el-row>
    </el-form>
    <template slot="footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确认</el-button>
    </template>
  </el-dialog>
</template>

<script>
import debounce from 'lodash/debounce'
export default {
  data () {
    return {
      visible: false,
      menuList: [],
      dataForm: {
        id: '',
        roleName: '',
        menuIdList: [],
        bak: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        roleName: [
          { required: true, message: "角色名不能为空", trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.$refs.menuListTree.setCheckedKeys([])
        Promise.all([
          this.getMenuList()
        ]).then(() => {
          if (this.dataForm.id) {
            this.getDetail()
          }
        })
      })
    },
    // 获取菜单列表
    getMenuList () {
      return this.$http.get('/system/menu/select').then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        this.menuList = res.data
      }).catch(() => {})
    },
    // 获取详细信息
    getDetail () {
      this.$http.get(`/system/role/detail/${this.dataForm.id}`).then(({ data: res }) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        this.dataForm = {
          ...this.dataForm,
          ...res.data
        }
        this.dataForm.menuIdList.forEach(item => this.$refs.menuListTree.setChecked(item, true))
      }).catch(() => {})
    },
    // 表单提交
    dataFormSubmitHandle: debounce(function () {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) {
          return false
        }
        this.dataForm.menuIdList = [
          ...this.$refs.menuListTree.getHalfCheckedKeys(),
          ...this.$refs.menuListTree.getCheckedKeys()
        ]
        this.$http.post(!this.dataForm.id?'/system/role/save':'/system/role/update',this.dataForm).then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.$message({
            message: "操作成功",
            type: 'success',
            duration: 500,
            onClose: () => {
              this.visible = false
              this.$emit('refreshDataList')
            }
          })
        }).catch(() => {})
      })
    }, 1000, { 'leading': true, 'trailing': false })
  }
}
</script>
