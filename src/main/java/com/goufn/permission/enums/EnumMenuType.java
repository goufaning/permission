package com.goufn.permission.enums;

/**
 * 菜单类型枚举
 *
 * @author goufn
 * @version V1.0
 * @date 2020-07-10 11:34
 */
public enum EnumMenuType {

    /** 目录 */
    NONE(0),

    /** 菜单 */
    MENU(1),

    /** 按钮 */
    BUTTON(2);


    private int type;

    EnumMenuType(int type) {
        this.type = type;
    }
}
