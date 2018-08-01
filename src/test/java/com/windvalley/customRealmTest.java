package com.windvalley;

import com.windvalley.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by wangj on 18-8-1.
 */
public class customRealmTest {
    @Test
    public void testAuthentication(){
        CustomRealm customRealm = new CustomRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密方法
        matcher.setHashAlgorithmName("md5");
        //设置加密次数
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        //2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wangj", "wj");
        subject.login(token);
        System.out.println("Is Authenticated " + subject.isAuthenticated());

        //检查是否有授权
        subject.checkRole("admin");

        subject.checkPermission("user:delete");

        subject.logout();
        System.out.println("Is Authenticated " + subject.isAuthenticated());
    }
}
