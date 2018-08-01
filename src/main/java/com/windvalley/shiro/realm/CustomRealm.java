package com.windvalley.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义授权登录
 * Created by wangj on 18-8-1.
 */
public class CustomRealm extends AuthorizingRealm {
    Map<String, String> users = new HashMap<String, String>();
    {
        users.put("wangj", "b91cf4b76c227b6e52ae9f38a62d0cd2");
        users.put("chengb", "044d32d77aa67e75b314fc8569a8be26");
        super.setName("CUSTOMREALM");
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = getRolesByUserName(userName);

        Set<String> permissions = getPermissionByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /**
     * 模拟获得权限数据信息，此处用Set模拟
     * @param userName
     * @return
     */
    private Set<String> getPermissionByUserName(String userName) {
        Set<String> permissions = new HashSet<String>();
        permissions.add("user:add");
        permissions.add("user:delete");
        return permissions;
    }

    /**
     * 模拟获得角色数据信息，此处用Set模拟
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从主体床传递过来的认证信息中，获得用户名
        String userName = (String) authenticationToken.getPrincipal();

        //通过用户名到数据源获得数据凭证
        String password = getPasswordByUserName(userName);
        if (password != null){
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("wangj", password, "CUSTOMREALM");
            //加入盐值，应为password为加过盐得到的数值
            authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));
            return authenticationInfo;
        }

        return null;
    }

    /**
     * 模拟获得数据信息，此处用Map模拟
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return users.get(userName);
    }

    public static void main(String[] args){
        Md5Hash md5Hash1 = new Md5Hash("wj", "salt");
        System.out.println(md5Hash1.toString());

        Md5Hash md5Hash2 = new Md5Hash("cb", "salt");
        System.out.println(md5Hash2.toString());
    }
}
