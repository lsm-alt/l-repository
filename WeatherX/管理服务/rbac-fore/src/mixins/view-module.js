export default {
    data() {
        /* eslint-disable */
        return {
            // 设置属性
            mixinViewModuleOptions: {
                createdIsNeed: true, // 此页面是否在创建时，调用查询数据列表接口？
                activatedIsNeed: false, // 此页面是否在激活（进入）时，调用查询数据列表接口？
                getDataListURL: '', // 数据列表接口，API地址
                getDataListIsPage: false, // 数据列表接口，是否需要分页？
                deleteURL: '', // 删除接口，API地址
                deleteIsBatch: false, // 删除接口，是否需要批量？
                deleteIsBatchKey: 'id', // 删除接口，批量状态下由那个key进行标记操作？比如：pid，uid...
                exportURL: '', // 导出接口，API地址
                downloadURL: '', //下载接口，API地址
                downloadIsBatch: false//下载接口，是否需要批量？
            },
            // 默认属性
            downUrl: '',
            dataForm: {}, // 查询条件
            dataList: [], // 数据列表
            order: '', // 排序，asc／desc
            orderField: '', // 排序，字段
            page: 1, // 当前页码
            limit: 10, // 每页数
            total: 0, // 总条数
            dataListLoading: false, // 数据列表，loading状态
            dataListSelections: [], // 数据列表，多选项
            addOrUpdateVisible: false // 新增／更新，弹窗visible状态
        }
        /* eslint-enable */
    },
    created() {
        if (this.mixinViewModuleOptions.createdIsNeed) {
            this.query()
        }
    },
    activated() {
        if (this.mixinViewModuleOptions.activatedIsNeed) {
            this.query()
        }
    },
    methods: {
        // 获取数据列表
        query() {
            this.dataListLoading = true
            this.$http.get(
                this.mixinViewModuleOptions.getDataListURL, {
                params: {
                    order: this.order,
                    orderField: this.orderField,
                    page: this.mixinViewModuleOptions.getDataListIsPage ? this.page : null,
                    limit: this.mixinViewModuleOptions.getDataListIsPage ? this.limit : null,
                    ...this.dataForm
                }
            }
            ).then(({ data: res }) => {
                this.dataListLoading = false;
                if (res.code !== 0) {
                    this.dataList = [];
                    this.total = 0;
                    return this.$message.error(res.msg);
                }
                this.dataList = this.mixinViewModuleOptions.getDataListIsPage ? res.data.list : res.data;
                this.total = this.mixinViewModuleOptions.getDataListIsPage ? res.data.total : 0;
            }).catch(() => {
                this.dataListLoading = false;
            })
        },
        // 多选
        dataListSelectionChangeHandle(val) {
            this.dataListSelections = val;
        },
        // 排序
        dataListSortChangeHandle(data) {
            if (!data.order || !data.prop) {
                this.order = '';
                this.orderField = '';
                return false;
            }
            this.order = data.order.replace(/ending$/, '');
            this.orderField = data.prop.replace(/([A-Z])/g, '_$1').toLowerCase();
            this.query();
        },
        // 分页, 每页条数
        pageSizeChangeHandle(val) {
            this.page = 1;
            this.limit = val;
            this.query();
        },
        // 分页, 当前页
        pageCurrentChangeHandle(val) {
            this.page = val;
            this.query();
        },
        getDataList: function () {
            this.page = 1;
            this.query();
        },
        // 新增 / 修改   
        addOrUpdateHandle(id) {
            this.addOrUpdateVisible = true;
            this.$nextTick(() => {
                this.$refs.addOrUpdate.dataForm.id = id;
                this.$refs.addOrUpdate.init();
            })
        },
        //指定操作类型的 (操作类型在 @/utils/Constants.js中定义)新增 / 修改  
        addOrUpdateHandle(id, action) {
            this.addOrUpdateVisible = true
            this.$nextTick(() => {
                this.$refs.addOrUpdate.dataForm.id = id;
                this.$refs.addOrUpdate.action = action;
                this.$refs.addOrUpdate.init();
            })
        },
        // 关闭当前窗口
        closeCurrentTab(data) {
            var tabName = this.$store.state.contentTabsActiveName;
            this.$store.state.contentTabs = this.$store.state.contentTabs.filter(item => item.name !== tabName);
            if (this.$store.state.contentTabs.length <= 0) {
                this.$store.state.sidebarMenuActiveName = this.$store.state.contentTabsActiveName = 'home';
                return false;
            }
            if (tabName === this.$store.state.contentTabsActiveName) {
                this.$router.push({ name: this.$store.state.contentTabs[this.$store.state.contentTabs.length - 1].name });
            }
        },
        // 删除
        deleteHandle(id) {
            if (this.mixinViewModuleOptions.deleteIsBatch && !id && this.dataListSelections.length <= 0) {
                return this.$message({
                    message: "请选择要删除项",
                    type: 'warning',
                    duration: 500
                })
            }
            this.$confirm("确认执行删除操作?", "注意", {
                confirmButtonText: "确认",
                cancelButtonText: "取消",
                type: 'warning'
            }).then(() => {
                let loading = this.$loading({
                    lock: true,
                    text: "执行删除中...",
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                this.$http.post(
                    `${this.mixinViewModuleOptions.deleteURL}${this.mixinViewModuleOptions.deleteIsBatch ? '' : '/' + id}`,
                    this.mixinViewModuleOptions.deleteIsBatch ? id ? [id] : this.dataListSelections.map(item => item[this.mixinViewModuleOptions.deleteIsBatchKey]) : {}
                ).then(({ data: res }) => {
                    loading.close();
                    if (res.code !== 0) {
                        return this.$message.error(res.msg)
                    }
                    this.$message({
                        message: "操作成功",
                        type: 'success',
                        duration: 500,
                        onClose: () => {
                            this.query()
                        }
                    })
                }).catch(() => {
                    loading.close();
                })
            }).catch(() => { })
        },
        downloadHandle(id) {
            if (!id) {
                return this.$message({
                    message: "请选择要下载项",
                    type: 'warning',
                    duration: 500
                })
            }
            this.$http.get(`${this.mixinViewModuleOptions.downloadURL}/${id}`).then(({ data: res }) => {
                if (res.code !== 0) {
                    return this.$message.error(res.msg)
                }

                this.downUrl = `${window.SITE_CONFIG["apiURL"]}` + "/security/getfile?filename=" + encodeURIComponent(res.data) + "&time=" + new Date().getTime();
                this.$message({
                    message: "操作成功",
                    type: 'success',
                    duration: 500,
                })
            }).catch(() => { })

        },
    }
}