package com.maggie.measure.client;

/**
 * 代理模式[[ 客户端--》代理对象--》目标对象 ]]
 *
 * @author Chris
 * @version 1.0.0
 * @date 2021/01/24
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("**********************CGLibProxy**********************");
        //        CGLibProxy cgLibProxy = new CGLibProxy();
        //        CGLibProxy userManager = (CGLibProxy) cgLibProxy.createProxyObject(new CGLibProxy());

        //        System.out.println("**********************JDKProxy**********************");
        //        JDKProxy jdkPrpxy = new JDKProxy();
        //        IUserManager userManagerJDK = (IUserManager) jdkPrpxy.newProxy(new UserManagerImpl());
        //        userManagerJDK.addUser("jpeony", "123456");
    }
}