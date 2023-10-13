package com.lzb.component.domain.aggregate;

/**
 * <br/>
 * Created on : 2023-09-12 13:32
 * @author lizebin
 */
public abstract class BaseBuilder<T extends BaseEntity<T>> {

    /*@NotNull
    @SuppressWarnings("unchecked")
    public Type getType() {
        return ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T getInstance() {
        return SpringHelper.getBean((Class<? extends T>) ((ParameterizedType)getType()).getRawType());
    }*/

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
     * @param entity
     */
    protected void validate(T entity) {

    }

}
