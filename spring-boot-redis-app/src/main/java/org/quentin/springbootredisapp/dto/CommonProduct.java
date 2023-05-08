package org.quentin.springbootredisapp.dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "product")
public class CommonProduct implements Serializable {
    private static final long serialVersionUID = 427469340875587487L;
    @Id
    @GeneratedValue
    private Long productId;
    private String brand;
    private String name;
    private String info;
    private double price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CommonProduct{" +
                "productId=" + productId +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", price=" + price +
                '}';
    }


    public static final class ProductBuilder {
        private Long productId;
        private String brand;
        private String name;
        private String info;
        private double price;

        private ProductBuilder() {
        }

        public static ProductBuilder aCommonProduct() {
            return new ProductBuilder();
        }

        public ProductBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder info(String info) {
            this.info = info;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        public CommonProduct build() {
            CommonProduct commonProduct = new CommonProduct();
            commonProduct.setProductId(productId);
            commonProduct.setBrand(brand);
            commonProduct.setName(name);
            commonProduct.setInfo(info);
            commonProduct.setPrice(price);
            return commonProduct;
        }
    }
}
