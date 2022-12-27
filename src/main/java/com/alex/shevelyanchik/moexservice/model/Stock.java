package com.alex.shevelyanchik.moexservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Stock {
    String ticker;
    String figi;
    String name;
    String type;
    String currency;
    String source;
}
