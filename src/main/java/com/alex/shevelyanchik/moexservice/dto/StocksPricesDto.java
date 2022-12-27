package com.alex.shevelyanchik.moexservice.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class StocksPricesDto {
    List<StockPriceDto> prices;
}
