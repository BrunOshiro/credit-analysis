package com.jazztech.creditanalysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysis;
import com.jazztech.creditanalysis.infrastructure.clientsapi.ClientApi;
import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiDto;
import com.jazztech.creditanalysis.infrastructure.exceptions.ClientNotFound;
import com.jazztech.creditanalysis.infrastructure.repository.Mapper;

import com.jazztech.creditanalysis.infrastructure.repository.Repository;
import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
import com.jazztech.creditanalysis.presentation.dto.RequestDto;
import com.jazztech.infrastructure.Factory;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CreditAnalysisServiceTest {
    @Mock
    Repository repository;
    @Mock
    ClientApi clientApi;
    @InjectMocks
    CreditAnalysis creditAnalysis;
    @Captor
    ArgumentCaptor<UUID> idClientArgumentCaptor;
    @Spy
    private Mapper mapper = new MapperImpl();
    @Captor
    private ArgumentCaptor<Entity> entityArgumentCaptor;

    @Test
    public void should_throws_client_not_found_exception() {
        final ClientApiDto clientApiDto = Factory.clientApiDtoFactory().toBuilder()
                .id(UUID.fromString("ff1efc87-a9c1-4f08-8fb7-1cd71d92dd6d"))
                .build();
        when(clientApi.getClientById(idClientArgumentCaptor.capture())).thenReturn(clientApiDto);

        final RequestDto requestDto = Factory.requestDtoFactory();
        ClientNotFound clientNotFound = assertThrows(ClientNotFound.class,
                () -> creditAnalysis.create(requestDto));

        assertEquals(clientNotFound.getMessage(), "Client not found for the ID cc8fa891-1d04-4251-ac72-df946bbf7a4f");
    }

    @Test
    public void should_create_new_credit_analysis_response() {

    }
}
