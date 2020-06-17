package com.huang.spring.beans.factory.support;

import com.huang.spring.beans.factory.config.BeanDefinition;

import java.util.Objects;

/**
 * 通用的bean定义
 *
 * @author hsz
 * @data 2020/6/17 10:42:00
 */
public class GenericBeanDefinition implements BeanDefinition {

    private Class<?> beanClass;

    /**
     * 默认单例
     */
    private String scope = BeanDefinition.SCOPE_SINGLETON;

    private String factoryBeanName;

    private String factoryMethodName;

    private String initMethodName;

    private String destroyMethodName;

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {

    }

    @Override
    public boolean isLazyInit() {
        return false;
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    @Override
    public String getFactoryBeanName() {
        return this.factoryBeanName;
    }

    @Override
    public void setFactoryMethodName(String factoryMethodName) {
        this.factoryMethodName = factoryMethodName;
    }

    @Override
    public String getFactoryMethodName() {
        return this.factoryMethodName;
    }

    @Override
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    @Override
    public String getInitMethodName() {
        return this.initMethodName;
    }

    @Override
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericBeanDefinition that = (GenericBeanDefinition) o;
        return Objects.equals(beanClass, that.beanClass) &&
                Objects.equals(scope, that.scope) &&
                Objects.equals(factoryBeanName, that.factoryBeanName) &&
                Objects.equals(factoryMethodName, that.factoryMethodName) &&
                Objects.equals(initMethodName, that.initMethodName) &&
                Objects.equals(destroyMethodName, that.destroyMethodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass, scope, factoryBeanName, factoryMethodName, initMethodName, destroyMethodName);
    }

    @Override
    public String toString() {
        return "GenericBeanDefinition{" +
                "beanClass=" + beanClass +
                ", scope='" + scope + '\'' +
                ", factoryBeanName='" + factoryBeanName + '\'' +
                ", factoryMethodName='" + factoryMethodName + '\'' +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                '}';
    }
}
