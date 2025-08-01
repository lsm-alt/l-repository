<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-system__role">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.forecastDate" placeholder="文件名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('system:role:save')" type="primary"
            @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-if="$hasPermission('system:role:delete')" type="danger" @click="deleteHandle()">删除</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="dataListLoading" :data="dataList" border @selection-change="dataListSelectionChangeHandle"
        @sort-change="dataListSortChangeHandle" style="width: 100%;">
        <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
        <el-table-column prop="forecastDate" label="文件名称" header-align="center" align="center"></el-table-column>
        <el-table-column prop="createDate" label="创建时间" sortable="custom" header-align="center"
          align="center"></el-table-column>
        <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
          <template slot-scope="scope">
            <el-button v-if="$hasPermission('system:role:update')" type="text" size="small"
              @click="downloadHandle(scope.row.id)">下载</el-button>
            <el-button v-if="$hasPermission('system:role:update')" type="text" size="small"
              @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
            <el-button v-if="$hasPermission('system:role:delete')" type="text" size="small"
              @click="deleteHandle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination :current-page="page" :page-sizes="[10, 20, 50, 100]" :page-size="limit" :total="total"
        layout="total, sizes, prev, pager, next, jumper" @size-change="pageSizeChangeHandle"
        @current-change="pageCurrentChangeHandle">
      </el-pagination>
      <!-- 弹窗, 新增 / 修改 -->
      <iframe :src="downUrl" style="display: none;"></iframe>
      <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    </div>
  </el-card>
</template>

<script>
import mixinViewModule from '@/mixins/view-module'
import AddOrUpdate from './forecast-add-or-update'
export default {
  mixins: [mixinViewModule],
  data() {
    return {
      mixinViewModuleOptions: {
        getDataListURL: '/weather/forecast/query',
        getDataListIsPage: true,
        deleteURL: '/weather/forecast/delete',
        deleteIsBatch: true,
        downloadURL: '/weather/forecast/download',
        downloadIsBatch: false
      },
      dataForm: {
        forecastDate: ''
      }
    }
  },
  components: {
    AddOrUpdate
  },
   activated() {
        this.downUrl = '';
    },
}
</script>
