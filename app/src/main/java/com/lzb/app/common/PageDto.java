package com.lzb.app.common;

import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import one.util.streamex.StreamEx;

/**
 * <br/>
 * Created on : 2023-09-22 23:07
 * @author mac
 */
@Getter
public class PageDto<T> {

    private final long pages;
    private final long pageSize;
    private final long total;
    private final List<T> rows;

    private <E> PageDto(long pages, long pageSize, long total, List<E> rows, Function<E, T> convertor) {
        this.pages = pages;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = StreamEx.of(rows).map(convertor).toList();
    }

    public static <E, T> PageDto<T> of(long pages, long pageSize, long total, List<E> rows, Function<E, T> convertor) {
        return new PageDto<>(pages, pageSize, total, rows, convertor);
    }
}
