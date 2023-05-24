package com.jazztech.creditanalysis.presentation.controller;

import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysis;
import com.jazztech.creditanalysis.applicationservice.service.Search;
import com.jazztech.creditanalysis.infrastructure.exceptions.ClientNotFound;
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
@RequestMapping("api/v.1.0.0/credit/analysis")
@RequiredArgsConstructor
@Validated
public class Controller {
    public static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private final CreditAnalysis creditAnalysis;
    private final Search search;

    //Credit Analysis Creation
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDto createCreditAnalysis(@RequestBody @Valid RequestDto requestDto) throws ClientNotFound {
        LOGGER.info("Credit Analysis request: " + requestDto.toString());
        return creditAnalysis.create(requestDto);
    }

    //Search by CPF or ClientId
    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResponseDto> getCreditAnalysisByCpfOrClientId(
            @RequestParam(value = "clientId", required = false) UUID clientId,
            @RequestParam(value = "cpf") @Valid String cpf
    ) {
        if (clientId != null) {
            LOGGER.info("Search Credit Analysis by ClientId: " + clientId + "performed successfully");
            return search.byClientId(clientId);
        } else {
            LOGGER.info("Search Credit Analysis by CPF: " + cpf + "performed successfully");
            return search.byCpf(cpf);
        }
    }

    //Search all Contracts
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResponseDto> getAllCreditAnalysis() {
        LOGGER.info("Search All Credit Analysis");
        return search.all();
    }
}
