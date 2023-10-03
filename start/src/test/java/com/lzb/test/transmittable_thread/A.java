package com.lzb.test.transmittable_thread;

import java.lang.annotation.Annotation;

import jakarta.annotation.Resource;

/**
 * <br/>
 * Created on : 2023-09-30 22:27
 * @author lizebin
 */
public class A implements Resource {

    @Override
    public String name() {
        return null;
    }

    @Override
    public String lookup() {
        return null;
    }

    @Override
    public Class<?> type() {
        return null;
    }

    @Override
    public AuthenticationType authenticationType() {
        return null;
    }

    @Override
    public boolean shareable() {
        return false;
    }

    @Override
    public String mappedName() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
