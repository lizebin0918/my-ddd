package com.lzb.component.supplier;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class MapMemoizeSupplier<K, V> extends MemoizeSupplier<Map<K, V>> {


	public MapMemoizeSupplier(Supplier<Map<K, V>> supplier) {
		super(supplier);
	}

	@Override
	protected Map<K, V> doGetData() {
		return Collections.unmodifiableMap(super.doGetData());
	}

	public Supplier<Optional<V>> getValueSupplierOpt(K key) {
		return () -> Optional.ofNullable(get().get(key));
	}

	public Supplier<V> getValueSupplier(K key, V defaultValue) {
		return () -> Optional.ofNullable(get().get(key)).orElse(defaultValue);
	}

}