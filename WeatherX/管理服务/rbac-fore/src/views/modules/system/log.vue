<template>
  <el-card shadow="never" class="aui-card--fill">
    <div class="mod-system__log">
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item>
          <el-input
            v-model="dataForm.username"
            placeholder="用户名"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="dataForm.result"
            clearable
            placeholder="日志操作结果"
          >
            <el-option :key="0" :value="0" label="失败"></el-option>
            <el-option :key="1" :value="1" label="成功"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="dataListLoading"
        :data="dataList"
        border
        @selection-change="dataListSelectionChangeHandle"
        @sort-change="dataListSortChangeHandle"
        style="width: 100%"
      >
        <el-table-column
          prop="username"
          label="用户名"
          sortable="custom"
          header-align="center"
          align="center"
        >
        </el-table-column>

        <el-table-column prop="type" label="操作类型" align="center">
        </el-table-column>
        <el-table-column label="日志操作结果" align="center">
          <template slot-scope="scope">
            <el-tag size="small" type="success" v-if="scope.row.result == 1">
              成功</el-tag
            >
            <el-tag size="small" type="danger" v-else> 失败</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="操作时间"
          align="center"
          sortable="custom"
        >
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="addOrUpdateHandle(scope.row.id)"
              >详情
            </el-button>
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
        @current-change="pageCurrentChangeHandle"
      >
      </el-pagination>
      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        @refreshDataList="getDataList"
      ></add-or-update>
    </div>
  </el-card>
</template>

<script>
import mixinViewModule from "@/mixins/view-module";
import AddOrUpdate from "./log-detail.vue";
export default {
  mixins: [mixinViewModule],
  data() {
    return {
      mixinViewModuleOptions: {
        getDataListIsPage: true,
        getDataListURL: "/system/log/query",
      },
      dataForm: {
        result: "",
        username: ""
      }
    };
  },
  components: {
    AddOrUpdate,
  },
};
</script>

<style scoped>
</style>
