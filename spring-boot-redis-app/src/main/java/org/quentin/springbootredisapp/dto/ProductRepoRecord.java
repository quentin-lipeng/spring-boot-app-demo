package org.quentin.springbootredisapp.dto;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 1. repo id
 */
@Entity(name = "repo_record")
public class ProductRepoRecord implements Serializable {

    private static final long serialVersionUID = -255573171217809848L;
    @Id
    @GeneratedValue
    private Long recordId;

    /**
     * the cascade property which let recordRepo insert a product before record
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private CommonProduct product;

    private Integer amount;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public CommonProduct getProduct() {
        return product;
    }

    public void setProduct(CommonProduct product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "ProductRepoRecord{" +
                "recordId=" + recordId +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }

    public static final class RecordBuilder {
        private Long recordId;
        private CommonProduct product;
        private Integer amount;

        private RecordBuilder() {
        }

        public static RecordBuilder aProductRepoRecord() {
            return new RecordBuilder();
        }

        public RecordBuilder recordId(Long recordId) {
            this.recordId = recordId;
            return this;
        }

        public RecordBuilder product(CommonProduct product) {
            this.product = product;
            return this;
        }

        public RecordBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public ProductRepoRecord build() {
            ProductRepoRecord productRepoRecord = new ProductRepoRecord();
            productRepoRecord.setRecordId(recordId);
            productRepoRecord.setProduct(product);
            productRepoRecord.setAmount(amount);
            return productRepoRecord;
        }
    }
}
