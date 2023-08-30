package com.lzb.domain.common.repository;


import com.lzb.domain.common.BaseAggregate;

/**
 * 新增聚合<br/>
 * Created on : 2022-03-10 09:59
 *
 * @author lizebin
 */
public interface AddRepository<R extends BaseAggregate<R>> {

    /**
     * 新增聚合根
     * @param aggregate
     */
    long add(R aggregate);

}
