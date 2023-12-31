package com.lzb.component.domain.repository;


import java.util.Optional;

import com.lzb.component.domain.aggregate.BaseAggregation;
import com.lzb.component.domain.annotation.Snapshot;



/**
 * 获取聚合根<br/>
 * Created on : 2022-03-10 10:09
 *
 * @author lizebin
 */
public interface GetRepository<R extends BaseAggregation<R>> {

    /**
     * 根据id查询
     *
     * @Snapshot 不生效，需要换一种方式，详见：{@link SnapshotAspect}
     *
     * @param id
     * @return
     */
    Optional<R> get(long id);

    /**
     * 根据id查询，不存在则抛异常
     * @param id
     * @return
     */
    @Snapshot
    default R getOrThrow(long id) {
        return get(id).orElseThrow();
    }

}
