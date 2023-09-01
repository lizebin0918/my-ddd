package com.lzb.component.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.javers.common.reflection.ReflectionUtil;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.custom.CustomValueComparator;
import org.jetbrains.annotations.Nullable;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

@Slf4j
public class MyDiff<T extends Serializable> {

    public static final String ID_FIELD = "id";

    private static final NegativeIdCounter ID_COUNTER = NegativeIdCounter.newInstance(Long.MIN_VALUE, -1);

    private static final Javers javers = JaversBuilder.javers()
            .withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
            // BigDecimal比较问题，将equal修改为compareTo, new BigDecimal("1.2")和new BigDecimal("1.20")
            .registerValue(BigDecimal.class, new CustomFixedEqualBigDecimalComparator())
            .build();

    private final Diff diff;

    public MyDiff(@Nullable T oldVersion, @Nullable T currentVersion) {
        setNegativeIdIfIdIsNull(oldVersion, currentVersion);
        this.diff = javers.compare(oldVersion, currentVersion);
        setNullIfNegativeId(currentVersion);
    }


    /**
     * @param oldList       旧list
     * @param newList       新list
     * @param clazz         class
     * @param addConsume    新增
     * @param updateConsume 修改
     * @param removeConsume 删除
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> void listChangeFunction(List<T> oldList,
            List<T> newList,
            Class<T> clazz,
            Consumer<List<T>> addConsume,
            Consumer<List<T>> updateConsume,
            Consumer<List<T>> removeConsume) {

        // 检查是否有@Id标记的字段
        checkForId(clazz);
        initNegativeId(clazz, newList);
        Diff listDiff = javers.compareCollections(oldList, newList, clazz);
        Map<String, T> addMap = new HashMap<>();
        Map<String, T> removeMap = new HashMap<>();
        Map<String, T> updateMap = new HashMap<>();

        for (Change change : listDiff.getChanges()) {
            if ((change instanceof NewObject)) {
                Object newObject = change.getAffectedObject()
                        .get();
                setNullIfNegativeId((T) newObject);
                addMap.put(change.getAffectedLocalId().toString(), (T) newObject);
            }

            if ((change instanceof ObjectRemoved)) {
                removeMap.put(change.getAffectedLocalId().toString(), (T) change.getAffectedObject()
                        .get());
            }

            if ((change instanceof PropertyChange)) {
                if (change.getAffectedLocalId() != null && !addMap.containsKey(change.getAffectedLocalId()
                        .toString())) {
                    updateMap.put(change.getAffectedLocalId().toString(), (T) change.getAffectedObject()
                            .get());
                }
            }
        }

        Assert.notNull(addConsume, "addConsume不能为空");
        Assert.notNull(addMap.values(), "addMap不能为空");
        addConsume.accept(new ArrayList<>(addMap.values()));

        Assert.notNull(updateConsume, "updateConsume不能为空");
        Assert.notNull(updateMap.values(), "upateMap不能为空");
        updateConsume.accept(new ArrayList<>(updateMap.values()));

        Assert.notNull(removeConsume, "removeConsume不能为空");
        Assert.notNull(removeMap.values(), "removeMap不能为空");
        removeConsume.accept((new ArrayList<>(removeMap.values())));
    }

    public boolean hasChanges() {
        return diff.hasChanges();
    }

    /**
     * id初始化负数
     *
     * @param clazz
     * @param list
     * @param <T>
     */
    public static <T extends Serializable> void initNegativeId(Class<T> clazz, Collection<T> list) {
        // 检查是否有id 属性
        checkForId(clazz);
        for (T t : list) {
            if (Objects.isNull(t)) {
                continue;
            }
            Object id = ReflectUtil.getFieldValue(t, ID_FIELD);
            if (Objects.isNull(id)) {
                ReflectUtil.setFieldValue(t, ID_FIELD, ID_COUNTER.get());
            }
        }
    }

    /**
     * 默认初始化负数
     *
     * @param old
     * @param current
     * @param <T>
     */
    public static <T extends Serializable> void setNegativeIdIfIdIsNull(T old, T current) {
        if (Objects.isNull(current)) {
            return;
        }

        Class<? extends Serializable> klass = current.getClass();
        // 检查是否有id 属性
        checkForId(klass);
        Object id = ReflectUtil.getFieldValue(current, ID_FIELD);
        if (Objects.isNull(id)) {
            long value = ID_COUNTER.get();
            ReflectUtil.setFieldValue(current, ID_FIELD, value);
            if (Objects.nonNull(old)) {
                ReflectUtil.setFieldValue(old, ID_FIELD, value);
            }
        }
    }

    /**
     * 必须有一个字段声明@Id
     * @param clazz
     * @param <T>
     */
    private static <T extends Serializable> void checkForId(Class<T> clazz) {
        boolean hasId = ReflectionUtil.getAllFields(clazz)
                .stream()
                .anyMatch(f -> f.looksLikeId());
        if (!hasId) {
            throw new IllegalArgumentException(clazz.getName() + ":未标记@Id注解，无法比较");
        }
    }

    public Changes getChanges() {
        return diff.getChanges();
    }


    public boolean propertyHasChange(String propertyName) {
        return propertyHasChange(Lists.newArrayList(propertyName));
    }

    /**
     * 判断对象里面的属性值是否发生了变化,list需要单独判断,对象也需要单独判断
     *
     * @param propertyNameList 属性值
     * @return true 表示发生了变化
     */
    public boolean propertyHasChange(List<String> propertyNameList) {
        return propertyNameList.stream()
                .anyMatch(s -> !diff.getPropertyChanges(s).isEmpty());
    }

    public static <T extends Serializable> void setNullIfNegativeId(T entity) {
        if (Objects.isNull(entity)) {
            return;
        }

        // 检查是否有id 属性
        checkForId(entity.getClass());
        Object id = ReflectUtil.getFieldValue(entity, ID_FIELD);
        if (Objects.nonNull(id)) {
            if (id instanceof Number && new BigDecimal(Objects.toString(id)).compareTo(BigDecimal.ZERO) < 0) {
                ReflectUtil.setFieldValue(entity, ID_FIELD, id);
            }
        }
    }

    private static class CustomFixedEqualBigDecimalComparator implements CustomValueComparator<BigDecimal> {

        @Override
        public boolean equals(BigDecimal a, BigDecimal b) {
            return a.compareTo(b) == 0;
        }

        @Override
        public String toString(BigDecimal value) {
            return value.toPlainString();
        }
    }

    private static class NegativeIdCounter {

        @Getter
        final long minValue;

        @Getter
        final long maxValue;

        final AtomicLong counter;

        NegativeIdCounter(long minValue, long maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.counter = new AtomicLong(minValue);
        }

        static NegativeIdCounter newInstance(long minValue, long maxValue) {
            if (minValue > maxValue) {
                throw new IllegalArgumentException("参数非法");
            }
            return new NegativeIdCounter(minValue, maxValue);
        }

        long get() {
            return counter.getAndUpdate(currentCount -> {
                if (currentCount >= maxValue) {
                    return currentCount + minValue + 1;
                }
                return currentCount + 1;
            });
        }
    }

}
