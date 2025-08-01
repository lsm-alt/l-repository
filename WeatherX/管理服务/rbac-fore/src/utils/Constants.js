const action = {
    query: {
        key: "query",
        value: "查询"
    },
    save: {
        key: "save",
        value: "保存"
    },
    update: {
        key: "update",
        value: "修改"
    },
    delete: {
        key: "delete",
        value: "删除"
    },
    detail: {
        key: "detail",
        value: "详情"
    },
    start: {
        key: "start",
        value: "开机"
    },
    stop: {
        key: "start",
        value: "关机"
    },
    restart: {
        key: "restart",
        value: "重启"
    },
    suspend: {
        key: "suspend",
        value: "挂起"
    },
    resume: {
        key: "resume",
        value: "恢复"
    },
    create: {
        key: "create",
        value: "创建"
    },
    apply: {
        key: "apply",
        value: "申请"
    },
    grant: {
        key: "grant",
        value: "授权"
    }
}
export default {
    token: "rbac-token",
    CREATE: action.create,
    QUERY: action.query,
    SAVE: action.save,
    UPDATE: action.update,
    DELETE: action.delete,
    DETAIL: action.detail,
    START: action.start,
    STOP: action.stop,
    RESTART: action.restart,
    SUSPEND: action.suspend,
    RESUME: action.resume,
    APPLY: action.apply,
    GRANT: action.grant
}