package com.alex.shevelyanchik.moexservice.service.impl;

import com.alex.shevelyanchik.moexservice.dto.BondDto;
import com.alex.shevelyanchik.moexservice.exception.LimitRequestsException;
import com.alex.shevelyanchik.moexservice.moexclient.CorporateBondsClient;
import com.alex.shevelyanchik.moexservice.moexclient.GovBondsClient;
import com.alex.shevelyanchik.moexservice.service.BondRepository;
import com.alex.shevelyanchik.moexservice.util.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoexBondRepository implements BondRepository {
    private final CorporateBondsClient corporateBondsClient;
    private final GovBondsClient govBondsClient;
    private final Parser parser;

    @Cacheable(value = "corps")
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

    @Cacheable(value = "govs")
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
