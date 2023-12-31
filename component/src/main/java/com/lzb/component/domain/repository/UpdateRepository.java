package com.lzb.component.domain.repository;


import com.lzb.component.domain.aggregate.BaseAggregation;
import com.lzb.component.domain.aop.UpdateAroundAspect;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * 更新聚合根<br/>
 * Created on : 2022-03-10 10:08
 *
 * @author lizebin
 */
@Validated
public interface UpdateRepository<R extends BaseAggregation<R>> {

    /**
     * 更新聚合根
     * 默认包含aop增强：校验聚合根是否有快照 ${@link UpdateAroundAspect}
     *
     * @param aggregate
     */
    void update(@NotNull(message = "聚合根不能为空") R aggregate);

}
