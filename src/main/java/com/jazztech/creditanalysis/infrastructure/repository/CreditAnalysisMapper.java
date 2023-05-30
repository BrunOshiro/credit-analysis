package com.jazztech.creditanalysis.infrastructure.repository;

import com.jazztech.creditanalysis.applicationservice.domain.entity.CreditAnalysisDomain;
import com.jazztech.creditanalysis.infrastructure.repository.entity.CreditAnalysisEntity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditAnalysisMapper {
    CreditAnalysisEntity domainToEntity(@Valid CreditAnalysisDomain creditAnalysisDomain);

    CreditAnalysisDomain dtoToDomain(@Valid RequestDto requestDto);

    @Mapping(source = "creationDate", target = "date")
    ResponseDto entityToDto(@Valid CreditAnalysisEntity creditAnalysisEntity);

    List<ResponseDto> listEntityToListDto(List<CreditAnalysisEntity> entities);
}
