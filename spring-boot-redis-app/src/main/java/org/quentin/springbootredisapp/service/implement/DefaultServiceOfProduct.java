package org.quentin.springbootredisapp.service.implement;

import org.quentin.springbootredisapp.dao.ProductRepository;
import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.exception.ProductException;
import org.quentin.springbootredisapp.exception.WrongOperatingException;
import org.quentin.springbootredisapp.service.ServiceOfProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * maybe get product with product's id or not and get with id which strange
 * todo I need reform this logic after
 */
@Service
public class DefaultServiceOfProduct implements ServiceOfProduct {
    public static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceOfProduct.class);
    private final ProductRepository productRepo;

    public DefaultServiceOfProduct(
            ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Cacheable(cacheNames = "products")
    @Override
    public List<CommonProduct> fetchProducts() {
        LOGGER.trace("fetch all products just called");
        return productRepo.findAll();
    }

    @Override
    public List<CommonProduct> fetchProductsWithBrand(String brand) {
        LOGGER.trace("fetch products with brand just called");

        return productRepo.findCommonProductsByBrand(brand);
    }

    @Caching(
            put = @CachePut(cacheNames = "product", key = "#product.productId"),
            evict = @CacheEvict(cacheNames = "products", allEntries = true)
    )
    @Override
    public CommonProduct updateProduct(CommonProduct product) {
        if (productRepo.existsById(product.getProductId())) {
            return productRepo.save(product);
        }
        throw new WrongOperatingException("the product not exist");
    }

    /**
     * <p>this method needn't be cached
     * because we can't get id if get no product with brand and name</p>
     */
    @Override
    public CommonProduct fetchProduct(CommonProduct product) {
        LOGGER.trace("fetch product just called");
        return product.getProductId() != null ? fetchProduct(product.getProductId()) :
                fetchProductWithBrandAndName(product.getBrand(), product.getName());
    }

    @Cacheable(cacheNames = "product", key = "#productId", unless = "#result.productId==null")
    @Override
    public CommonProduct fetchProduct(Long productId) {
        LOGGER.trace("fetch product with id just called");
        Optional<CommonProduct> productOptional = productRepo.findById(productId);
        if (!productOptional.isPresent()) {
            throw new ProductException(ProductException.PRODUCT_NOT_EXIST);
        }
        return productOptional.get();
    }

    @Override
    public Set<String> fetchBrands() {
        return productRepo.findAllBrands();
    }

    @Override
    public Set<Long> fetchProductIds() {
        return productRepo.findAllProductIds();
    }

    private CommonProduct fetchProductWithBrandAndName(String brand, String name) {
        return productRepo.findCommonProductByBrandAndName(brand, name)
                .orElseGet(CommonProduct::new);
    }

}
