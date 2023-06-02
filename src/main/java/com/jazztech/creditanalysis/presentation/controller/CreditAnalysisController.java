package com.jazztech.creditanalysis.presentation.controller;

import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysisSearch;
import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysisService;
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
// Minor: vamos utilizar até a versão minor, para não gerar muitas versões de APIs
// Entidades no plural "credits" ou "credit-analisys"
@RequestMapping("api/v.1.0.0/credit/analysis")
@RequiredArgsConstructor
@Validated
public class CreditAnalysisController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CreditAnalysisController.class);
    private final CreditAnalysisService creditAnalysisService;
    private final CreditAnalysisSearch creditAnalysisSearch;

    //Credit Analysis Creation
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    // Prefira trabahar com exceções unchecked, caso contrário terá que pançar por todas as camadas "acima"
    public ResponseDto createCreditAnalysis(@RequestBody @Valid RequestDto requestDto) throws ClientNotFound {
        LOGGER.info("Credit Analysis request: " + requestDto.toString());
        return creditAnalysisService.createCreditAnalysis(requestDto);
    }

    // Não é necessário um novo endpoint para consulta com query parameters, o id do cliente ou cpf são filtros no "getAll"
    //Search by CPF or ClientId
    @GetMapping("/")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResponseDto> getCreditAnalysisByCpfOrClientId(
            @RequestParam(value = "clientId", required = false) UUID clientId,
            @RequestParam(value = "cpf") @Valid String cpf
    ) {
        if (clientId != null) {
            LOGGER.info("Search Credit Analysis by ClientId: " + clientId + "performed successfully");
            return creditAnalysisSearch.byClientId(clientId);
        } else {
            LOGGER.info("Search Credit Analysis by CPF: " + cpf + "performed successfully");
            return creditAnalysisSearch.byCpf(cpf);
        }
    }

    //Search all Contracts
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResponseDto> getAllCreditAnalysis() {
        LOGGER.info("Search All Credit Analysis");
        return creditAnalysisSearch.all();
    }
}
