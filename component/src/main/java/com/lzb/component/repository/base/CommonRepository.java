package com.lzb.component.repository.base;


import com.lzb.component.aggregate.BaseAggregate;

/**
 * 通用仓储接口
 *
 * @author mac
 * @date 2022/11/01
 */
public interface CommonRepository<R extends BaseAggregate<R>>
        extends AddRepository<R>, UpdateRepository<R>,
        GetRepository<R> {

}
