package org.quentin.springbootredisapp.controller;

import org.quentin.springbootredisapp.dto.ProductRepoRecord;
import org.quentin.springbootredisapp.service.ServiceOfProductRepoRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiLocation.REPO_RECORD)
public class ControllerOfProductRepoRecord {

    public static final Logger LOGGER = LoggerFactory.getLogger(ControllerOfProductRepoRecord.class);
    private final ServiceOfProductRepoRecord recordS;

    public ControllerOfProductRepoRecord(ServiceOfProductRepoRecord recordS) {
        this.recordS = recordS;
    }

    @GetMapping("/all")
    public List<ProductRepoRecord> records() {
        return recordS.fetchRecords();
    }

    @PostMapping("")
    public ProductRepoRecord loadARecord(
            @RequestBody ProductRepoRecord record) {
        try {
            return recordS.writeRecord(record);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ProductRepoRecord();
        }
    }
}
