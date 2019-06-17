package com.goufn.permission.common.vo;

import com.goufn.permission.entity.SysPermission;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
    /** 名称 */
    private String title;
    /** 图标 */
    private String icon;
    /** 导航地址 */
    private String href;
    /** 是否默认展开 */
    private boolean spread;
    /** 子菜单 */
    private List<MenuVo> children;

    public MenuVo(SysPermission permission) {
        title = permission.getName();
        icon = permission.getIcon();
        href = permission.getUrl();
        spread = permission.isSpread();
    }


}
