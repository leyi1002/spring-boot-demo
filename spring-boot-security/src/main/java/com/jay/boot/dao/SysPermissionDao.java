package com.jay.boot.dao;

import com.jay.boot.bean.SysPermission;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface SysPermissionDao {

    List<SysPermission> findAll();

    List<SysPermission> findBySysUserId(int userId);
}
