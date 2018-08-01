package com.windvalley;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by wangj on 18-8-1.
 */
public class jdbcRealmTest {
    DruidDataSource druidDataSource = new DruidDataSource();

    {
        druidDataSource.setUrl("jdbc:sqlite:D:/Java/shiro/src/test/java/resources/shiro.db");
//        druidDataSource.setUsername("wangj");
//        druidDataSource.setPassword("wangj");
    }

    @Test
    public void testAuthentication() {
        JdbcRealm jdbcRealm = new JdbcRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //默认不会去查询权限数据,此处要设置为打开
        //jdbcRealm.setPermissionsLookupEnabled(true);

        //自定义查询数据用于认证数据
        jdbcRealm.setAuthenticationQuery("select password from users where name = ?");
        //jdbcRealm.setUserRolesQuery("select role_name from user_roles where username = ?");
        defaultSecurityManager.setRealm(jdbcRealm);

        //2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wangj", "123456");
        subject.login(token);
        System.out.println("Is Authenticated " + subject.isAuthenticated());

        //检查是否有授权
//        subject.checkRole("admin");

//        subject.checkPermission("user:delete");

        subject.logout();
        System.out.println("Is Authenticated " + subject.isAuthenticated());
    }
}
