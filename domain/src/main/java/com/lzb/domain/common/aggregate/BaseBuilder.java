package com.lzb.domain.common.aggregate;

/**
 * <br/>
 * Created on : 2023-09-12 13:32
 * @author lizebin
 */
public abstract class BaseBuilder<T extends BaseEntity<T>> {

    /**
     * 初始化对象
     * @return
     */
    public final T build() {
        T agg = doBuild();
        validate(agg);
        return agg;
    }

    /**
     * 只构造聚合根
     * @return
     */
    protected abstract T doBuild();

    /**
     * 子类重写，校验聚合根
     * @param agg
     */
    protected void validate(T agg) {

    }

}
