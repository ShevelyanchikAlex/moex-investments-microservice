package com.alex.shevelyanchik.moexservice.controller;

import com.alex.shevelyanchik.moexservice.dto.FigiesDto;
import com.alex.shevelyanchik.moexservice.dto.StocksDto;
import com.alex.shevelyanchik.moexservice.dto.StocksPricesDto;
import com.alex.shevelyanchik.moexservice.dto.TickersDto;
import com.alex.shevelyanchik.moexservice.service.impl.MoexBondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
public class MoexBondController {
    private final MoexBondService bondService;

    @PostMapping("/getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TickersDto tickersDto) {
        return bondService.getBondsFromExchange(tickersDto);
    }

    @PostMapping("/prices")
    public StocksPricesDto getPricesByFigies(@RequestBody FigiesDto figiesDto) {
        return bondService.getPricesByFigies(figiesDto);
    }
}
