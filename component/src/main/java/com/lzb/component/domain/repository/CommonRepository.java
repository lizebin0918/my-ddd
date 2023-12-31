package com.lzb.component.domain.repository;


import com.lzb.component.domain.aggregate.BaseAggregation;

/**
 * 通用仓储接口
 *
 * @author mac
 * @date 2022/11/01
 */
public interface CommonRepository<R extends BaseAggregation<R>>
        extends AddRepository<R>, UpdateRepository<R>,
        GetRepository<R> {

}
