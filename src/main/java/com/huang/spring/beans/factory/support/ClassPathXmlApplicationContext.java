package com.huang.spring.beans.factory.support;


import com.huang.spring.beans.factory.config.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author hsz
 * @data 2020/6/18 16:24:51
 */
public class ClassPathXmlApplicationContext extends DefaultBeanFactory {

    public ClassPathXmlApplicationContext() throws Exception {
        refresh();
    }

    public void refresh() throws Exception {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        loadBeanDefinitions("application.xml");
        finishBeanInitialization(beanFactory);
    }

    public void finishBeanInitialization(DefaultBeanFactory beanFactory) {
        Map<String, BeanDefinition> beanDefinitionMap = beanFactory.getBeanDefinitionMap();
        beanDefinitionMap.forEach((name, beanDefinition) -> {
            if ("scope".equals(beanDefinition.getScope()) && !beanDefinition.isLazyInit()) {
                try {
                    getBean(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadBeanDefinitions(String configLocation) throws Exception {
        List<Element> beans = getBeanElements(configLocation);
        for (Element bean : beans) {
            BeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(Class.forName(bean.attributeValue("class")));
            beanDefinition.setScope(bean.attributeValue("scope"));
            beanDefinition.setLazyInit(Boolean.parseBoolean(bean.attributeValue("lazy-init")));
            registerBeanDefinition(bean.attributeValue("id"), beanDefinition);
        }
    }

    private List<Element> getBeanElements(String configLocation) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(this.getClass().getClassLoader().getResource(configLocation).getPath()));
        Element beans = document.getRootElement();
        return beans.elements("bean");
    }
}
