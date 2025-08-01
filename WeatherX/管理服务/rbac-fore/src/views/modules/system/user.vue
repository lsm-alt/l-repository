<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-system__user">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.username" placeholder="用户名" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('system:user:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('system:user:delete')" type="danger" @click="deleteHandle()">删除</el-button>
        </el-form-item>
      </el-form>
      <el-table
        v-loading="dataListLoading"
        :data="dataList"
        border
        @selection-change="dataListSelectionChangeHandle"
        @sort-change="dataListSortChangeHandle"
        style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="username" label="用户名" sortable="custom" header-align="center" align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" header-align="center" align="center"></el-table-column>
        <el-table-column prop="mobile" label="手机号" sortable="custom" header-align="center" align="center"></el-table-column>
        <el-table-column prop="gender" label="性别" sortable="custom" header-align="center" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.gender === 0" size="small" type="primary">男</el-tag>
            <el-tag v-if="scope.row.gender === 1" size="small" type="danger">女</el-tag>
            <el-tag v-if="scope.row.gender === 2" size="small" type="info">保密</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" sortable="custom" header-align="center" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 0" size="small" type="danger">停用</el-tag>
            <el-tag v-else size="small" type="success">正常</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom" header-align="center" align="center" width="180"></el-table-column>
        <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('system:user:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
            <el-button v-if="$hasPermission('system:user:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="limit"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="pageSizeChangeHandle"
        @current-change="pageCurrentChangeHandle">
      </el-pagination>
      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    </div>
  </el-card>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import AddOrUpdate from './user-add-or-update'
export default {
  mixins: [mixinViewModule],
  data () {
    return {
      mixinViewModuleOptions: {
        getDataListURL: '/system/user/query',
        getDataListIsPage: true,
        deleteURL: '/system/user/delete',
        deleteIsBatch: true
      },
      dataForm: {
        username: '',
      }
    }
  },
  components: {
    AddOrUpdate
  }
}
</script>
