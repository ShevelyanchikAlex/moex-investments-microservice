package com.alex.shevelyanchik.moexservice.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BondDto {
    String ticker;
    String name;
    BigDecimal price;
}
