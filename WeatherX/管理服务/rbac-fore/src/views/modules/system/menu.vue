<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-system__menu">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-button v-if="$hasPermission('system:menu:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" row-key="id" border style="width: 100%;">
        <el-table-column prop="name" label="名称" header-align="center" min-width="150"></el-table-column>
        <el-table-column prop="icon" label="图标" header-align="center" align="center">
          <template slot-scope="scope">
            <svg class="icon-svg" aria-hidden="true"><use :xlink:href="`#${scope.row.icon}`"></use></svg>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型" header-align="center" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type === 0" size="small">菜单</el-tag>
            <el-tag v-else size="small" type="info">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" header-align="center" align="center"></el-table-column>
        <el-table-column prop="url"  label="路由" header-align="center" align="center" width="150" :show-overflow-tooltip="true" ></el-table-column>
        <el-table-column prop="permissions" label="授权标识" header-align="center" align="center" width="150" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column  label="操作" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('system:menu:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
            <el-button v-if="$hasPermission('system:menu:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    </div>
  </el-card>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import AddOrUpdate from './menu-add-or-update'
export default {
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getDataListURL: '/system/menu/list',
        deleteURL: '/system/menu/delete'
      }
    }
  },
  components: {
    AddOrUpdate
  }
}
</script>
