package com.migros.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

// Lombok for getters, setter, constructor etc.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Store {
    // Tried to clean and simple entity

    @Id
    @NotNull(message = "Store Id cannot be null")
    // @JsonIgnore  if don't show identities in json file
    private Long storeId;

    @NotNull(message = "Store Name cannot be null")
    private String storeName;
    private BigDecimal storeLat;
    private BigDecimal storeLgt;


}