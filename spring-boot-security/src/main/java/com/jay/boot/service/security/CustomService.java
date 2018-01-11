package com.jay.boot.service.security;

import com.jay.boot.bean.SysPermission;
import com.jay.boot.bean.SysRole;
import com.jay.boot.bean.SysUser;
import com.jay.boot.dao.SysPermissionDao;
import com.jay.boot.dao.SysUserDao;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
@Service
public class CustomService implements UserDetailsService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysPermissionDao sysPermissionDao;


    //根据角色拥有的权限去判断权限
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser sysUser = sysUserDao.findByUserName(username);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SysPermission> permissions = sysPermissionDao.findBySysUserId(sysUser.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(SysPermission permission:permissions)
        {
            authorities.add(new SimpleGrantedAuthority(permission.getUrl()));
            System.out.println(permission.getName());
        }
        return new org.springframework.security.core.userdetails.User(sysUser.getUsername(),
                sysUser.getPassword(), authorities);
    }

//    根据角色判断权限
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        SysUser sysUser = sysUserDao.findByUserName(username);
//        if(sysUser == null){
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
//        for(SysRole role:sysUser.getRoles())
//        {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//            System.out.println(role.getName());
//        }
//        return new org.springframework.security.core.userdetails.User(sysUser.getUsername(),
//                sysUser.getPassword(), authorities);
//    }
}
