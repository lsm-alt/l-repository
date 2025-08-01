package com.codedata.rbac.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuType {
    /**
     * 菜单
     */
    MENU(0),
    /**
     * 按钮
     */
    BUTTON(1);
    @EnumValue
    @JsonValue
    private int value;

    MenuType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
