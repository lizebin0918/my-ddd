package com.lzb.test.transmittable_thread;

import com.alibaba.ttl.TransmittableThreadLocal;

public class Command {

    static ThreadLocal<String> name = new ThreadLocal<>();
    static InheritableThreadLocal<String> key = new InheritableThreadLocal<>();
    static TransmittableThreadLocal<String> codec = new TransmittableThreadLocal<>();

    public static String getName() {
        return name.get();
    }

    public static String getKey() {
        return key.get();
    }

    public static String getCodec() {
        return codec.get();
    }

    public static void setName(String name) {
        Command.name.set(name);
    }

    public static void setKey(String key) {
        Command.key.set(key);
    }

    public static void setCodec(String codec) {
        Command.codec.set(codec);
    }

}
