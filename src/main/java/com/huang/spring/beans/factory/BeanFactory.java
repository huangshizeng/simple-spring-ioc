package com.huang.spring.beans.factory;

/**
 * Bean工厂
 *
 * @author hsz
 * @data 2020/6/17 09:07:49
 */
public interface BeanFactory {

    /**
     * 根据name获取bean实例
     *
     * @param name bean名称
     * @return bean实例
     * @throws Exception 异常
     */
    Object getBean(String name) throws Exception;

    /**
     * 判断是否包含指定name的bean
     *
     * @param name bean名称
     * @return true 存在叫name的bean
     */
    boolean containsBean(String name);

    /**
     * 判断是否单例
     *
     * @param name bean名称
     * @return true 单例
     * @throws Exception 不存在这个bean实例
     */
    boolean isSingleton(String name) throws Exception;

    /**
     * 判断是否原型
     *
     * @param name bean名称
     * @return true 原型
     * @throws Exception 不存在这个bean实例
     */
    boolean isPrototype(String name) throws Exception;
}
