package com.jazztech.creditanalysis.presentation.controller;

import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysisSearch;
import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysisService;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/credits/analysis")
@RequiredArgsConstructor
@Validated
public class CreditAnalysisController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CreditAnalysisController.class);
    private final CreditAnalysisService creditAnalysisService;
    private final CreditAnalysisSearch creditAnalysisSearch;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDto createCreditAnalysis(@RequestBody @Valid RequestDto requestDto) {
        LOGGER.info("Credit Analysis request: " + requestDto.toString());
        return creditAnalysisService.createCreditAnalysis(requestDto);
    }

    @GetMapping("/search")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResponseDto> searchAllCreditAnalysis(
            @RequestParam(value = "clientId", required = false) UUID clientId,
            @RequestParam(value = "cpf", required = false) @Valid String cpf
    ) {
        if (clientId != null) {
            LOGGER.info("Search Credit Analysis by ClientId: " + clientId + "performed successfully");
            return creditAnalysisSearch.byClientId(clientId);
        } else if (cpf != null) {
            LOGGER.info("Search Credit Analysis by CPF: " + cpf + "performed successfully");
            return creditAnalysisSearch.byCpf(cpf);
        } else {
            LOGGER.info("Search All Credit Analysis performed successfully");
            return creditAnalysisSearch.all();
        }
    }
}
