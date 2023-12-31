package com.lzb.component.domain.repository;


import com.lzb.component.domain.aggregate.BaseAggregation;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * 新增聚合<br/>
 * Created on : 2022-03-10 09:59
 *
 * @author lizebin
 */
@Validated
public interface AddRepository<R extends BaseAggregation<R>> {

    /**
     * 新增聚合根
     * @param aggregate
     */
    long add(@NotNull(message = "聚合根不能为空") R aggregate);

}
