package com.lzb.component.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class DateUtils {
    /**
     * GMT ZoneId
     */
    private static final ZoneId GMT_ZONE_ID = ZoneId.of("GMT");

    /**
     * 时间转时间戳
     *
     * @param date Date类型的时间
     * @return unix time
     */
    public static int toUnix(Date date) {
        return (int) date.toInstant().getEpochSecond();
    }

    /**
     * 默认东八区
     *
     * @param time
     * @return
     */
    public static Long toUnix(LocalDateTime time) {
        if (Objects.isNull(time)) {
            return null;
        }
        return toUnix(time, ZoneOffset.of("+8"));
    }

    /**
     * 设置ZoneOffset
     *
     * @param time
     * @param zoneOffset
     * @return
     */
    public static Long toUnix(LocalDateTime time, ZoneOffset zoneOffset) {
        if (Objects.isNull(time)) {
            return null;
        }
        return time.toInstant(zoneOffset).toEpochMilli();
    }
}