package com.jay.boot.dao.impl;

/**
 * Created by Administrator on 2017/12/26.
 */
public class BaseDao {


    public String getNameSapce(){
        return getClass().getInterfaces()[0].getName();
    }

}
