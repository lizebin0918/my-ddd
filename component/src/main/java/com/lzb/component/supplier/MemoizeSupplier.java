package com.lzb.component.supplier;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

public class MemoizeSupplier<T> implements Supplier<T> {

	/**
	 * 数据获取方式
	 */
	private final Supplier<T> supplier;

	public MemoizeSupplier(Supplier<T> supplier) {
		this.supplier = Suppliers.synchronizedSupplier(Suppliers.memoize(supplier::get));
	}

	@Override
	public T get() {
		return supplier.get();
	}

	protected T doGetData() {
		return supplier.get();
	}

}