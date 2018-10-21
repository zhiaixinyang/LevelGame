package com.mdove.levelgame.utils.permission;

/**
 * @author MDove on 2018/4/16.
 */
public interface PermissionGrantCallback {

    /**
     * 权限请求被批准
     *
     * @param requestCode
     */
    void permissionGranted(int requestCode);

    /**
     * 权限请求被拒绝
     *
     * @param requestCode true 需要告知申请该权限的原因， false 请忽略
     */
    void permissionRefused(int requestCode);
}
