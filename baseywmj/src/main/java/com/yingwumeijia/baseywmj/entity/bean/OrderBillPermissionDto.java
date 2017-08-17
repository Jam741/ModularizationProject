package com.yingwumeijia.baseywmj.entity.bean;

/**
 * Created by jamisonline on 2017/6/14.
 */

public class OrderBillPermissionDto {

//    {
//        "canView": false,
//            "permissionType": "",
//            "url": ""
//    }


//    canView (boolean, optional): 是否可以查看此账单,
//    permissionType (string, optional): 权限类型（int，0 = 无权查看，1 = 可以查看直接跳转，2 = 订单已关闭可以查看，3 = 账单已取消不可以查看,
//    url (string, optional)

    boolean canView;
    int permissionType;
    String url;

    public boolean isCanView() {
        return canView;
    }

    public void setCanView(boolean canView) {
        this.canView = canView;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
