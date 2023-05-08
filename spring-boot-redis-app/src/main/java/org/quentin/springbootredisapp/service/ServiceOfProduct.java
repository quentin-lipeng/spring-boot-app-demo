package org.quentin.springbootredisapp.service;

import org.quentin.springbootredisapp.dto.CommonProduct;

import java.util.List;
import java.util.Set;

/**
 * add
 * update
 * select
 * product shouldn't be deleted
 */
public interface ServiceOfProduct {

    List<CommonProduct> fetchProducts();

    List<CommonProduct> fetchProductsWithBrand(String brand);

    CommonProduct updateProduct(CommonProduct product);

    CommonProduct fetchProduct(CommonProduct product);

    CommonProduct fetchProduct(Long productId);

    Set<String> fetchBrands();

    Set<Long> fetchProductIds();
}
