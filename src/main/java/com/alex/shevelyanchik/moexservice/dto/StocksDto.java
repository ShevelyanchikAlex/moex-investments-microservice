package com.alex.shevelyanchik.moexservice.dto;

import com.alex.shevelyanchik.moexservice.model.Stock;
import lombok.Value;

import java.util.List;

@Value
public class StocksDto {
    List<Stock> stocks;
}
