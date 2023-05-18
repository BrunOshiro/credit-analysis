package com.jazztech.creditanalysis.infrastructure.repository;

import com.jazztech.creditanalysis.applicationservice.domain.entity.Domain;
import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.validation.Valid;
import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Entity domainToEntity(@Valid Domain domain);

    Domain dtoToDomain(@Valid RequestDto requestDto);

    ResponseDto entityToDto(@Valid Entity entity);

    List<ResponseDto> listEntityToListDto(List<Entity> entities);
}
