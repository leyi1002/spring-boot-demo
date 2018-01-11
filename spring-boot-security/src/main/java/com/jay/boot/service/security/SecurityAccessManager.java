package com.jay.boot.service.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/12/27.
 */
@Service
public class SecurityAccessManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;

        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {
                matcher = new AntPathRequestMatcher("/login");
                if (matcher.matches(request)) {
                    return;
                }
            } else {
                matcher = new AntPathRequestMatcher(ga.getAuthority());
                if (matcher.matches(request)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }
//    @Override
//    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        //configAttributes 为SecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
//        //此方法是为了判定用户请求的url是否在权限表(sys_permission)中，如果在权限表中，则返回给 decide 方法，
//        //用来判定用户是否有此权限。如果不在权限表中则放行。
//        if(configAttributes == null || configAttributes.size() <=0){
//            return;
//        }
//        ConfigAttribute c;
//        String needAuthority;
//        Iterator<ConfigAttribute> iter = configAttributes.iterator();
//        while(iter.hasNext()) {
//            c = iter.next();
//            needAuthority = c.getAttribute();
//            //authentication 为在CustomService 中循环添加到 GrantedAuthority 对象中的权限信息集合
//            for(GrantedAuthority ga : authentication.getAuthorities()) {
//                if(needAuthority.trim().equals(ga.getAuthority())) {
//                    return;
//                }
//            }
//        }
//        throw new AccessDeniedException("no right");
//    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
