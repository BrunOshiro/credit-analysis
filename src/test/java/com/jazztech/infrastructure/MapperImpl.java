package com.jazztech.infrastructure;


import com.jazztech.creditanalysis.applicationservice.domain.entity.Domain;
import com.jazztech.creditanalysis.infrastructure.repository.Mapper;
import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2023-05-23T14:11:03-0300",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.1 (Oracle Corporation)"
)
@Component
public class MapperImpl implements Mapper {

    @Override
    public Entity domainToEntity(Domain domain) {
        if (domain == null) {
            return null;
        }

        UUID clientId = null;
        String clientCpf = null;
        String clientName = null;
        BigDecimal monthlyIncome = null;
        BigDecimal requestedAmount = null;
        Boolean approved = null;
        BigDecimal approvedLimit = null;
        BigDecimal annualInterest = null;
        BigDecimal withdraw = null;

        clientId = domain.clientId();
        clientCpf = domain.clientCpf();
        clientName = domain.clientName();
        if (domain.monthlyIncome() != null) {
            monthlyIncome = BigDecimal.valueOf(domain.monthlyIncome());
        }
        if (domain.requestedAmount() != null) {
            requestedAmount = BigDecimal.valueOf(domain.requestedAmount());
        }
        approved = domain.approved();
        if (domain.approvedLimit() != null) {
            approvedLimit = BigDecimal.valueOf(domain.approvedLimit());
        }
        if (domain.annualInterest() != null) {
            annualInterest = BigDecimal.valueOf(domain.annualInterest());
        }
        if (domain.withdraw() != null) {
            withdraw = BigDecimal.valueOf(domain.withdraw());
        }

        Entity entity =
                new Entity(clientId, clientCpf, clientName, monthlyIncome, requestedAmount, approved, approvedLimit, annualInterest, withdraw);

        return entity;
    }

    @Override
    public Domain dtoToDomain(RequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Domain.DomainBuilder domain = Domain.builder();

        domain.clientId(requestDto.clientId());
        if (requestDto.monthlyIncome() != null) {
            domain.monthlyIncome(requestDto.monthlyIncome().doubleValue());
        }
        if (requestDto.requestedAmount() != null) {
            domain.requestedAmount(requestDto.requestedAmount().doubleValue());
        }

        return domain.build();
    }

    @Override
    public ResponseDto entityToDto(Entity entity) {
        if (entity == null) {
            return null;
        }

        LocalDateTime date = null;
        UUID id = null;
        Boolean approved = null;
        BigDecimal approvedLimit = null;
        BigDecimal withdraw = null;
        BigDecimal annualInterest = null;
        UUID clientId = null;

        date = entity.getCreationDate();
        id = entity.getId();
        approved = entity.getApproved();
        approvedLimit = entity.getApprovedLimit();
        withdraw = entity.getWithdraw();
        annualInterest = entity.getAnnualInterest();
        clientId = entity.getClientId();

        ResponseDto responseDto = new ResponseDto(id, approved, approvedLimit, withdraw, annualInterest, clientId, date);

        return responseDto;
    }

    @Override
    public List<ResponseDto> listEntityToListDto(List<Entity> entities) {
        if (entities == null) {
            return null;
        }

        List<ResponseDto> list = new ArrayList<ResponseDto>(entities.size());
        for (Entity entity : entities) {
            list.add(entityToDto(entity));
        }

        return list;
    }
}
