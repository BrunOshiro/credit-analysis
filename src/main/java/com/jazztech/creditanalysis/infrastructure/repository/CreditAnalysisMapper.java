package com.jazztech.creditanalysis.infrastructure.repository;

import com.jazztech.creditanalysis.applicationservice.domain.entity.CreditAnalysisDomain;
import com.jazztech.creditanalysis.infrastructure.repository.entity.CreditAnalysisEntity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreditAnalysisMapper {
    @BeforeMapping
    default void generateId(@MappingTarget CreditAnalysisEntity creditAnalysisEntity) {
        creditAnalysisEntity.setId(UUID.randomUUID());
    }

    CreditAnalysisEntity domainToEntity(@Valid CreditAnalysisDomain creditAnalysisDomain);

    CreditAnalysisDomain dtoToDomain(@Valid RequestDto requestDto);

    @Mapping(source = "creationDate", target = "date")
    ResponseDto entityToDto(@Valid CreditAnalysisEntity creditAnalysisEntity);

    List<ResponseDto> listEntityToListDto(List<CreditAnalysisEntity> entities);
}
