package com.jay.boot.dao.read;

import com.jay.boot.bean.Zoo;

/**
 * Created by Administrator on 2018/3/2.
 */
public interface ZooReadDao {

    Zoo findByName(String name);
}
