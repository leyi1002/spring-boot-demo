package com.jay.boot.dao;

import com.jay.boot.bean.SysRole;

/**
 * Created by Administrator on 2017/12/26.
 */
public interface SysRoleDao {

    SysRole findByName(String name);
}
