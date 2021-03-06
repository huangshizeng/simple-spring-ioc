package com.huang.spring.beans.factory.support;

import com.huang.spring.beans.factory.BeanFactory;
import com.huang.spring.beans.factory.config.BeanDefinition;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的bean工厂
 *
 * @author hsz
 * @data 2020/6/17 11:15:29
 */

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

    /**
     * 存放bean定义
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 存放单例bean实例
     */
    private Map<String, Object> beanMap = new ConcurrentHashMap<>(256);

    /**
     * 根据bean的名字获取bean实例,里面主要做的工作是创建bean实例和对bean实例进行初始化
     *
     * @param name bean名称
     * @return bean实例
     */
    @Override
    public Object getBean(String name) throws Exception {
        Object bean = beanMap.get(name);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            return null;
        }
        Class<?> type = beanDefinition.getBeanClass();
        if (type != null) {
            // bean类型不为空，并且工厂方法名为空，说明是使用构造方法创建bean
            if (beanDefinition.getFactoryMethodName() == null) {
                bean = createBeanByConstructor(beanDefinition);
            } else {
                // 使用静态工厂方法创建bean
                bean = createBeanByStaticFactoryMethod(beanDefinition);
            }
        } else {
            // 使用工厂bean方法创建bean
            bean = createBeanByFactoryBean(beanDefinition);
        }
        // 初始化方法
        doInit(beanDefinition, bean);
        // 如果是单例，则将实例添加到beanMap中，后面获取bean时直接从map中获取
        if (beanDefinition.isSingleton()) {
            beanMap.put(name, bean);
        }
        return bean;
    }

    @Override
    public boolean containsBean(String name) {
        return beanMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) throws Exception {
        return false;
    }

    @Override
    public boolean isPrototype(String name) throws Exception {
        return false;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws Exception {
        beanDefinitionMap.remove(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws Exception {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    /**
     * 使用构造方法创建bean
     *
     * @param beanDefinition bean定义
     * @return bean实例
     */
    protected Object createBeanByConstructor(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException {
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * 使用静态工厂方法创建bean
     *
     * @param beanDefinition bean定义
     * @return bean实例
     */
    protected Object createBeanByStaticFactoryMethod(BeanDefinition beanDefinition) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Method method = beanClass.getMethod(beanDefinition.getFactoryMethodName());
        return method.invoke(beanClass);
    }

    protected Object createBeanByFactoryBean(BeanDefinition beanDefinition) throws Exception {
        Object factoryBean = getBean(beanDefinition.getFactoryBeanName());
        Method method = factoryBean.getClass().getMethod(beanDefinition.getFactoryMethodName());
        return method.invoke(factoryBean);
    }

    /**
     * 执行初始化方法
     *
     * @param beanDefinition bean定义
     * @param bean           bean实例
     */
    protected void doInit(BeanDefinition beanDefinition, Object bean) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String initMethodName = beanDefinition.getInitMethodName();
        if (initMethodName != null) {
            bean.getClass().getMethod(initMethodName).invoke(bean);
        }
    }

    /**
     * 容器销毁
     */
    @Override
    public void close() {
        beanDefinitionMap.forEach((name, beanDefinition) -> {
            if (beanDefinition.isSingleton() && beanDefinition.getDestroyMethodName() != null) {
                Object bean = beanMap.get(name);
                Method method;
                try {
                    method = bean.getClass().getMethod(beanDefinition.getDestroyMethodName());
                    method.invoke(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Map<String, BeanDefinition> getBeanDefinitionMap() {
        return beanDefinitionMap;
    }
}
