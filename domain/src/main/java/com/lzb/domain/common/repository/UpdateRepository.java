package com.lzb.domain.common.repository;


import com.lzb.domain.common.aggregate.BaseAggregate;

/**
 * 更新聚合根<br/>
 * Created on : 2022-03-10 10:08
 *
 * @author lizebin
 */
public interface UpdateRepository<R extends BaseAggregate<R>> {

    /**
     * 更新聚合根
     * 默认包含aop增强：校验聚合根是否有快照 ${@link com.lzb.infr.aop.UpdateAroundAspect}
     *
     * @param aggregate
     */
    void update(R aggregate);

}
