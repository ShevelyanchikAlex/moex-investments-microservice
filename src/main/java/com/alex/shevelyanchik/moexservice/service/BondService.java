package com.alex.shevelyanchik.moexservice.service;

import com.alex.shevelyanchik.moexservice.dto.FigiesDto;
import com.alex.shevelyanchik.moexservice.dto.StocksDto;
import com.alex.shevelyanchik.moexservice.dto.StocksPricesDto;
import com.alex.shevelyanchik.moexservice.dto.TickersDto;

public interface BondService {
    StocksDto getBondsFromExchange(TickersDto tickersDto);

    StocksPricesDto getPricesByFigies(FigiesDto figiesDto);
}
