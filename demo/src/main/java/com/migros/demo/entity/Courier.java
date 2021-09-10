package com.migros.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Lombok for getters, setter, constructor etc.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Courier {
    // Tried to clean and simple entity

    @Id
    @NotNull(message = "Courier Id cannot be null")
    // @JsonIgnore  if don't show identities in json file
    private Long courierId;

    @NotNull(message = "Courier Name cannot be null")
    private String courierName;
    private LocalDateTime time;

    @Min(value = 0, message = "Total distance must be positive")
    private Double totalDistance;
    private BigDecimal courierLat;
    private BigDecimal courierLgt;

}
