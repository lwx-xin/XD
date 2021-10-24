package priv.xin.xd.core.entity;

import java.util.Date;

public class Menu {
    private String menuId;

    private String menuText;

    private String menuParent;

    private Integer menuOrder;

    private String menuUrl;

    private String menuGroup;

    private String menuIcon;

    private String menuDelFlag;

    private Date menuCreateTime;

    private String menuCreateUser;

    private Date menuModifyTime;

    private String menuModifyUser;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText == null ? null : menuText.trim();
    }

    public String getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent == null ? null : menuParent.trim();
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(String menuGroup) {
        this.menuGroup = menuGroup == null ? null : menuGroup.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public String getMenuDelFlag() {
        return menuDelFlag;
    }

    public void setMenuDelFlag(String menuDelFlag) {
        this.menuDelFlag = menuDelFlag == null ? null : menuDelFlag.trim();
    }

    public Date getMenuCreateTime() {
        return menuCreateTime;
    }

    public void setMenuCreateTime(Date menuCreateTime) {
        this.menuCreateTime = menuCreateTime;
    }

    public String getMenuCreateUser() {
        return menuCreateUser;
    }

    public void setMenuCreateUser(String menuCreateUser) {
        this.menuCreateUser = menuCreateUser == null ? null : menuCreateUser.trim();
    }

    public Date getMenuModifyTime() {
        return menuModifyTime;
    }

    public void setMenuModifyTime(Date menuModifyTime) {
        this.menuModifyTime = menuModifyTime;
    }

    public String getMenuModifyUser() {
        return menuModifyUser;
    }

    public void setMenuModifyUser(String menuModifyUser) {
        this.menuModifyUser = menuModifyUser == null ? null : menuModifyUser.trim();
    }
}