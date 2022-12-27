package com.alex.shevelyanchik.moexservice.service.impl;

import com.alex.shevelyanchik.moexservice.dto.BondDto;
import com.alex.shevelyanchik.moexservice.dto.StocksDto;
import com.alex.shevelyanchik.moexservice.dto.TickersDto;
import com.alex.shevelyanchik.moexservice.exception.LimitRequestsException;
import com.alex.shevelyanchik.moexservice.model.Currency;
import com.alex.shevelyanchik.moexservice.model.Stock;
import com.alex.shevelyanchik.moexservice.moexclient.CorporateBondsClient;
import com.alex.shevelyanchik.moexservice.moexclient.GovBondsClient;
import com.alex.shevelyanchik.moexservice.service.BondService;
import com.alex.shevelyanchik.moexservice.util.parser.Parser;
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
    private final CorporateBondsClient corporateBondsClient;
    private final GovBondsClient govBondsClient;
    private final Parser parser;

    @Override
    public StocksDto getBondsFromExchange(TickersDto tickersDto) {
        List<BondDto> bonds = new ArrayList<>();
        bonds.addAll(getCorporateBonds());
        bonds.addAll(getGovBonds());
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
    public List<BondDto> getCorporateBonds() {
        String moexXml = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(moexXml);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting corporate bonds.");
        }
        return bondDtos;
    }

    @Override
    public List<BondDto> getGovBonds() {
        String moexXml = govBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(moexXml);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting government bonds.");
            throw new LimitRequestsException("Moex isn't answering for getting government bonds.");
        }
        return bondDtos;
    }
}
