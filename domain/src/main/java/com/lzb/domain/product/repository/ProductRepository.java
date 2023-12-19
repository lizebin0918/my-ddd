package com.lzb.domain.product.repository;

import com.lzb.component.domain.repository.CacheRepository;
import com.lzb.component.domain.repository.CommonRepository;
import com.lzb.domain.product.aggregation.Product;

/**
 * <br/>
 * Created on : 2023-12-19 13:47
 * @author lizebin
 */
public interface ProductRepository extends CommonRepository<Product>, CacheRepository<Product> {

}
