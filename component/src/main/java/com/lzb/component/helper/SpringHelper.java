package com.lzb.component.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Component
public class SpringHelper implements ApplicationContextAware, EnvironmentAware {

    private ApplicationContext applicationContext;
    private StandardEnvironment enviroment;
    private static final Pattern PATTERN = Pattern.compile("(?<=\\$\\{)[A-Za-z_\\-0-9.]+");

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.enviroment = (StandardEnvironment) environment;
    }

    public Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public String getProperty(String propertyName, String defaultValue) {
        return enviroment.getProperty(real(propertyName), defaultValue);
    }

    public <T> T getProperty(String propertyName, Class<T> targetType, T defaultValue) {
        return enviroment.getProperty(real(propertyName), targetType, defaultValue);
    }

    private static String real(String propertyName) {
        if (propertyName.startsWith("${")) {
            Matcher matcher = PATTERN.matcher(propertyName);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return propertyName;
    }

}