package com.alex.shevelyanchik.moexservice.util.parser;

import com.alex.shevelyanchik.moexservice.dto.BondDto;

import java.util.List;

public interface Parser {
    List<BondDto> parse(String rates);
}
