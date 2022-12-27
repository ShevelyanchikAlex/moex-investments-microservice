package com.alex.shevelyanchik.moexservice.service;

import com.alex.shevelyanchik.moexservice.dto.BondDto;
import com.alex.shevelyanchik.moexservice.dto.StocksDto;
import com.alex.shevelyanchik.moexservice.dto.TickersDto;

import java.util.List;

public interface BondService {
    StocksDto getBondsFromExchange(TickersDto tickersDto);

    List<BondDto> getCorporateBonds();

    List<BondDto> getGovBonds();
}
