package com.alex.shevelyanchik.moexservice.service.impl;

import com.alex.shevelyanchik.moexservice.dto.BondDto;
import com.alex.shevelyanchik.moexservice.dto.StocksDto;
import com.alex.shevelyanchik.moexservice.dto.TickersDto;
import com.alex.shevelyanchik.moexservice.model.Currency;
import com.alex.shevelyanchik.moexservice.model.Stock;
import com.alex.shevelyanchik.moexservice.service.BondRepository;
import com.alex.shevelyanchik.moexservice.service.BondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
