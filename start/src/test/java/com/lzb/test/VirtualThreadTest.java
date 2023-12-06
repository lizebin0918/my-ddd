package com.lzb.test;

import com.lzb.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-12-06 13:42
 * @author lizebin
 */
public class VirtualThreadTest extends BaseUnitTest {

    @Test
    @DisplayName("虚拟线程")
    void should_virtual_thread() {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " Hello, lizebin");
        };

        // 使用静态构建器方法
        Thread virtualThread = Thread.startVirtualThread(runnable);
    }

}
