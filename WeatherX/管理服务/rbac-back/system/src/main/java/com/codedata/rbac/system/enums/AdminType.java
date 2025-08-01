package com.codedata.rbac.system.enums;

public enum AdminType {
    SUPER_ADMIN(1),
    ORDINARY(0);
    private int value;
    AdminType(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
