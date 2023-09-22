package com.lzb.component.supplier;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import io.vavr.Function2;

public class LazySupplier<T> implements Supplier<T> {

	/**
	 * 数据
	 */
	protected volatile T data;
	/**
	 * 数据获取方式
	 */
	private final Supplier<T> supplier;

	public LazySupplier(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	@Override
	public T get() {
		if (data == null) {
			synchronized (this) {
				if (data == null) {
					data = doGetData();
				}
			}
		}
		return data;
	}

	protected T doGetData() {
		return supplier.get();
	}

	/**
	 * 构造
	 * @param supplier 原始supplier
	 * @return com.shopcider.oms.component.function.LazySupplier<T>
	 * @author 张子宽
	 * @date 2022/11/02
	 */
	public static <T> LazySupplier<T> build(Supplier<T> supplier) {
		return new LazySupplier<>(supplier);
	}


	/**
	 * 构造
	 *
	 * @param supplier 原始supplier
	 * @return com.shopcider.oms.component.function.LazySupplier<T>
	 * @author 张子宽
	 * @date 2022/11/02
	 */
	public static <K, V> MapLazySupplier<K, V> buildMap(Supplier<Map<K, V>> supplier) {
		return new MapLazySupplier<>(supplier);
	}


	/**
	 * 合并
	 * @param s1 原始supplier
	 * @param s2 原始supplier
	 * @return com.shopcider.oms.component.function.LazySupplier<T>
	 * @author 张子宽
	 * @date 2022/11/02
	 */
	public static <A, B, C> LazySupplier<C> combine(Supplier<A> s1, Supplier<B> s2, Function2<A, B, C> combiner) {
		return build(() -> combiner.apply(s1.get(), s2.get()));
	}

	public <R> LazySupplier<R> map(Function<T, R> mapping) {
		return build(() -> mapping.apply(get()));
	}

	public CompletableFuture<T> async() {
		if (Objects.nonNull(data)) {
			return CompletableFuture.completedFuture(data);
		}
		return CompletableFuture.supplyAsync(this, ThreadPoolConstant.QUERY);
	}
}