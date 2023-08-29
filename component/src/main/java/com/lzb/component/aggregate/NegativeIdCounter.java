package com.lzb.component.aggregate;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;

/**
 * 超过最大值会设置最小值
 */
class NegativeIdCounter {

    @Getter
    final long minValue;

    @Getter
    final long maxValue;

    final AtomicLong counter;

    NegativeIdCounter(long minValue, long maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.counter = new AtomicLong(minValue);
    }

    static NegativeIdCounter newInstance(long minValue, long maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("参数非法");
        }
        return new NegativeIdCounter(minValue, maxValue);
    }

    long get() {
        return counter.getAndUpdate(currentCount -> {
            if (currentCount >= maxValue) {
                return currentCount + minValue + 1;
            }
            return currentCount + 1;
        });
    }
}