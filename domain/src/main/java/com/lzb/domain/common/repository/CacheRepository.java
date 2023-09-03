package com.lzb.domain.common.repository;

import com.lzb.domain.common.BaseAggregate;

/**
 * <br/>
 * Created on : 2023-09-03 11:41
 * @author mac
 */
public interface CacheRepository<R extends BaseAggregate<R>> {

    /**
     * 从缓存获取聚合根（非实时数据），不能用于更新，没有快照
     * @param id
     * @return
     */
    R getInCache(long id);

}