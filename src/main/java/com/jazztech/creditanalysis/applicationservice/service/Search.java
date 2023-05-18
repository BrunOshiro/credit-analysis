package com.jazztech.creditanalysis.applicationservice.service;

import com.jazztech.creditanalysis.infrastructure.repository.Mapper;
import com.jazztech.creditanalysis.infrastructure.repository.Repository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Search {
    private static final Logger LOGGER = LoggerFactory.getLogger(Search.class);
    private final Repository repository;
    private final Mapper mapper;

    @Transactional
    public List<ResponseDto> byCpf(@Valid String cpf) {
        final List<Entity> entities = StringUtils.isBlank(cpf)
                ? repository.findAll() : repository.findByCpf(cpf);
        LOGGER.info("Credit Analysis Listed by CPF Successfully");
        return mapper.listEntityToListDto(entities);
    }

    @Transactional
    public List<ResponseDto> all() {
        final List<Entity> entities = repository.findAll();
        LOGGER.info("Credit Analysis listed Successfully");
        return mapper.listEntityToListDto(entities);
    }
}
