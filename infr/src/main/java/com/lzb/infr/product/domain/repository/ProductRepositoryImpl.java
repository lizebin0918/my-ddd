package com.lzb.infr.product.domain.repository;

import java.util.Optional;
import java.util.function.LongSupplier;

import com.lzb.domain.product.aggregation.Product;
import com.lzb.domain.product.repository.ProductRepository;
import com.lzb.infr.common.BaseRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-12-19 14:07
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl extends BaseRepository<Product> implements ProductRepository {


    @Override
    public Optional<Product> getInCache(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> get(long id) {
        return Optional.empty();
    }

    @Override
    public LongSupplier doAdd(Product aggregate) {
        return null;
    }

    @Override
    public Runnable doUpdate(Product aggregate) {
        return null;
    }
}
