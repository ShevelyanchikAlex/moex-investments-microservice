package com.alex.shevelyanchik.moexservice.service;

import com.alex.shevelyanchik.moexservice.dto.BondDto;

import java.util.List;

public interface BondRepository {
    List<BondDto> getCorporateBonds();

    List<BondDto> getGovBonds();
}
