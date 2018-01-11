package com.jay.boot.dao.impl;

import com.jay.boot.bean.SysUser;
import com.jay.boot.dao.SysUserDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/12/26.
 */
//@Component
//public class SysUserDaoImpl extends BaseDao implements SysUserDao{
//
//    @Autowired
//    private SqlSession sqlSession;
//
//    @Override
//    public SysUser findByUserName(String name) {
//        return sqlSession.selectOne(getNameSapce()+".findByUserName",name);
//    }
//}
