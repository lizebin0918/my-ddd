package com.lzb.component.supplier;

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

	public static <T> LazySupplier<T> build(Supplier<T> supplier) {
		return new LazySupplier<>(supplier);
	}

	public static <A, B, C> LazySupplier<C> combine(Supplier<A> s1, Supplier<B> s2, Function2<A, B, C> combiner) {
		return build(() -> combiner.apply(s1.get(), s2.get()));
	}

}