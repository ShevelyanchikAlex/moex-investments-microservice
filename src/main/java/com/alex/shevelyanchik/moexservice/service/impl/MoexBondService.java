package com.alex.shevelyanchik.moexservice.service.impl;

import com.alex.shevelyanchik.moexservice.dto.*;
import com.alex.shevelyanchik.moexservice.exception.BondNotFoundException;
import com.alex.shevelyanchik.moexservice.model.Currency;
import com.alex.shevelyanchik.moexservice.model.Stock;
import com.alex.shevelyanchik.moexservice.service.BondRepository;
import com.alex.shevelyanchik.moexservice.service.BondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoexBondService implements BondService {
    private final BondRepository bondRepository;

    @Override
    public StocksDto getBondsFromExchange(TickersDto tickersDto) {
        List<BondDto> bonds = new ArrayList<>();
        bonds.addAll(bondRepository.getCorporateBonds());
        bonds.addAll(bondRepository.getGovBonds());
        List<BondDto> resultBonds = bonds.stream()
                .filter(b -> tickersDto.getTickers().contains(b.getTicker()))
                .collect(Collectors.toList());
        List<Stock> stocks = resultBonds.stream()
                .map(bond -> Stock.builder()
                        .ticker(bond.getTicker())
                        .name(bond.getName())
                        .figi(bond.getTicker())
                        .type("Bond")
                        .currency(Currency.RUB.name())
                        .source("MOEX")
                        .build())
                .collect(Collectors.toList());
        return new StocksDto(stocks);
    }

    @Override
    public StocksPricesDto getPricesByFigies(FigiesDto figiesDto) {
        List<String> figies = new ArrayList<>(figiesDto.getFigies());
        List<BondDto> bonds = new ArrayList<>();
        bonds.addAll(bondRepository.getCorporateBonds());
        bonds.addAll(bondRepository.getGovBonds());
        figies.removeAll(bonds.stream().map(BondDto::getTicker).collect(Collectors.toList()));
        if (!figies.isEmpty()) {
            throw new BondNotFoundException(String.format("Bonds %s not found.", figies));
        }
        List<StockPriceDto> prices = bonds.stream()
                .filter(bondDto -> figiesDto.getFigies().contains(bondDto.getTicker()))
                .map(bondDto -> new StockPriceDto(bondDto.getTicker(), bondDto.getPrice().multiply(new BigDecimal(10))))
                .collect(Collectors.toList());
        return new StocksPricesDto(prices);
    }
}
