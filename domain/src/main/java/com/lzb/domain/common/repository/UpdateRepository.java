package com.lzb.domain.common.repository;


import com.lzb.domain.common.BaseAggregate;

/**
 * 更新聚合根<br/>
 * Created on : 2022-03-10 10:08
 *
 * @author lizebin
 */
public interface UpdateRepository<R extends BaseAggregate<R>> {

    /**
     * 更新聚合根
     * 默认包含aop增强：校验聚合根是否有快照 {@link UpdateAroundAspect}
     *
     * 20230616：aop切到接口方法上，这种做法不太好，别人完全不知道，推荐在实现方法声明注解
     *
     * @param aggregate
     */
    void update(R aggregate);

}
