package com.windvalley;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wangj on 18-8-1.
 */
public class authentication {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("wangj", "123456", "admin", "user");
    }

    @Test
    public void testAuthentication(){
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("wangj", "123456");
        subject.login(token);
        System.out.println("Is Authenticated " + subject.isAuthenticated());

        //检查是否有授权
        subject.checkRoles("admin", "user");

        subject.logout();
        System.out.println("Is Authenticated " + subject.isAuthenticated());
    }
}
