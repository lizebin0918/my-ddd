package com.lzb.component.repository.base;


import java.util.Optional;

import com.lzb.component.aggregate.BaseAggregate;
import com.lzb.component.repository.annotation.Snapshot;


/**
 * 获取聚合根<br/>
 * Created on : 2022-03-10 10:09
 *
 * @author lizebin
 */
public interface GetRepository<R extends BaseAggregate<R>> {

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
