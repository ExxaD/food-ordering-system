package com.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderAddress {

    @NotNull
    @Size(max = 50)
    private final String street;
    @NotNull
    @Size(max = 10)
    private final String postalCode;
    @NotNull
    @Size(max = 50)
    private final String city;
}
