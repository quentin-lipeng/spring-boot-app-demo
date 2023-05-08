package org.quentin.springbootredisapp.controller;

import org.quentin.springbootredisapp.dto.CommonProduct;
import org.quentin.springbootredisapp.exception.ProductException;
import org.quentin.springbootredisapp.exception.WrongOperatingException;
import org.quentin.springbootredisapp.service.ServiceOfProduct;
import org.quentin.springbootredisapp.vo.CommonResponse;
import org.quentin.springbootredisapp.vo.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(ApiLocation.PRODUCT)
public class ControllerOfProduct {

    private static final String ALL_PRODUCTS = "/all";
    private static final String ALL_BRAND_PRODUCT = "/all/brand/{brand}";
    private static final String ALL_ID_PRODUCT = "/all/id";
    private static final String A_PRODUCT = "";
    private static final String ALL_BRAND = "/brands";

    private final ServiceOfProduct productS;
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerOfProduct.class);

    public ControllerOfProduct(ServiceOfProduct productS) {
        this.productS = productS;
    }

    @GetMapping(ALL_PRODUCTS)
    public ResponseEntity<List<CommonProduct>> fetchProducts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productS.fetchProducts());
    }

    @GetMapping(ALL_BRAND_PRODUCT)
    public List<CommonProduct> fetchProductsWithBrand(@PathVariable String brand) {
        return productS.fetchProductsWithBrand(brand);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> fetchProductWithId(@PathVariable Long productId) {
        Assert.notNull(productId, "product's id can't be null");
        ProductResponse response = new ProductResponse();
        response.setData(productS.fetchProduct(productId));
        return ResponseEntity
                .ok().body(response);
    }

    @GetMapping(A_PRODUCT)
    public ResponseEntity<ProductResponse> fetchProduct(@RequestBody CommonProduct product) {
        Assert.notNull(product, "product can't be null");
        Assert.notNull(product.getBrand(), "product's brand can't be null");
        Assert.notNull(product.getName(), "product's name can't be null");
        ProductResponse response = new ProductResponse();
        response.setData(productS.fetchProduct(product));
        return ResponseEntity
                .ok().body(response);
    }

    /**
     * To change product price only
     */
    @PutMapping(A_PRODUCT)
    public CommonProduct putProduct(@RequestBody CommonProduct product) {
        Assert.notNull(product, "product can't be null");
        Assert.notNull(product.getProductId(), "product's id can't be null");
        CommonProduct productFromRepo = productS.fetchProduct(product);
        productFromRepo.setPrice(product.getPrice());
        return productS.updateProduct(productFromRepo);
    }

    @GetMapping(ALL_BRAND)
    public Set<String> allBrand() {
        return productS.fetchBrands();
    }

    @GetMapping(ALL_ID_PRODUCT)
    public ResponseEntity<Map<String, Set<Long>>> allIds() {
        Map<String, Set<Long>> response = new HashMap<>();
        response.put("data", productS.fetchProductIds());
        return ResponseEntity.ok(response);
    }

    private static class ProductResponse extends CommonResponse<CommonProduct> {
    }

    @ExceptionHandler(value = WrongOperatingException.class)
    public ResponseEntity<ExceptionResponse> processProductException(Exception e) {
        Assert.notNull(e, "Exception is null");
        ExceptionResponse response = new ExceptionResponse(e.getMessage());
        LOGGER.trace("is exception instance of {}", e instanceof ProductException);
        if (e.getMessage().equals(ProductException.PRODUCT_NOT_EXIST)) {
            String href = "http://localhost:8081" + ApiLocation.PRODUCT + ALL_ID_PRODUCT;
            response.setRecommendHref(href);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
