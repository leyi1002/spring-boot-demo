package com.jay.boot.dao;

import com.jay.boot.bean.SysUser;

/**
 * Created by Administrator on 2017/12/26.
 */
public interface SysUserDao {

    SysUser findByUserName(String name);
}
