<template>
    <el-dialog :visible.sync="visible" :close-on-click-modal="false" :close-on-press-escape="false">

        <el-descriptions title="日志详情" :column="3" border>
            <el-descriptions-item>
                <template slot="label">
                    用户名
                </template>
                {{ dataForm.username }}
            </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                    操作类型
                </template>
                {{ dataForm.type }}
            </el-descriptions-item> 
            <el-descriptions-item>
                <template slot="label">
                    耗时
                </template>
                {{ dataForm.time }} ms
            </el-descriptions-item>
            <el-descriptions-item :labelStyle="{
                    'width':'120px'}">
                <template slot="label">
                    日志操作结果
                </template>
                <el-tag size="small" type="success" v-if="dataForm.result == 1">
                    成功</el-tag>
                <el-tag size="small" type="danger" v-else>
                    失败</el-tag>
            </el-descriptions-item>
                   <el-descriptions-item>
                <template slot="label">
                    操作时间
                </template>
                {{ dataForm.createDate }}
            </el-descriptions-item>
            </el-descriptions>
            <el-descriptions :column="1" border>
            <el-descriptions-item :labelStyle="{
                    'width':'120px'}">
                <template slot="label">
                    ip地址
                </template>
                {{ dataForm.ip }}
            </el-descriptions-item>
             </el-descriptions>
            <el-descriptions :column="1" border>
            <el-descriptions-item contentClassName="enter" :labelStyle="{
                    'width':'120px'}">
                <template slot="label">
                    日志内容
                </template>
                {{ dataForm.content }}
            </el-descriptions-item>
            </el-descriptions>



        <template slot="footer">
            <el-button type="primary" @click="visible = false">关闭</el-button>
        </template>
    </el-dialog>
</template>

<script>
export default {
    data() {
        return {
            visible: false,
            dataForm: {
                id: null
            },
            successFlag: true
        };
    },
    methods: {
        init() {
            this.visible = true;
            this.$nextTick(() => {
                if (this.dataForm.id) {
                    this.getDetail();
                }
            });
        },
        // 获取详细信息
        getDetail() {
            this.$http
                .get(`/system/log/detail/${this.dataForm.id}`)
                .then(({ data: res }) => {
                    if (res.code !== 0) {
                        return this.$message.error(res.msg);
                    }
                    //vue中让接口返回得数据与自定义得数据合并
                    this.dataForm = {
                        ...this.dataForm,
                        ...res.data,
                    };
                })
                .catch(() => { });
        }
    }
};
</script>

<style>
.enter {
    white-space: pre-wrap;
}
</style>
