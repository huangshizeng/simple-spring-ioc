package com.huang.spring.beans.factory.test;

import com.huang.spring.beans.factory.support.ClassPathXmlApplicationContext;

/**
 * @author hsz
 * @data 2020/6/22 14:39:04
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        User user1 = (User) applicationContext.getBean("user");
        User user2 = (User) applicationContext.getBean("user");
        User user3 = (User) applicationContext.getBean("user");
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
    }
}
