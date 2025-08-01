package com.codedata.rbac.system.enums;
public enum UserStatus {
    DISABLE(0),//禁用状态
    ENABLED(1);//启用状态
    private int value;
    UserStatus(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
