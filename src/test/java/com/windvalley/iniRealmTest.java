package com.windvalley;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wangj on 18-8-1.
 */
public class iniRealmTest {
    @Test
    public void testAuthentication(){
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wangj", "123456");
        subject.login(token);
        System.out.println("Is Authenticated " + subject.isAuthenticated());

        //检查是否有授权
        subject.checkRole("admin");

        subject.checkPermission("user:delete");

        subject.logout();
        System.out.println("Is Authenticated " + subject.isAuthenticated());
    }
}
