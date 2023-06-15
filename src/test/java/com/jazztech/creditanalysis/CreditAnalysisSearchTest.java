package com.jazztech.creditanalysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.jazztech.creditanalysis.applicationservice.service.CreditAnalysisSearch;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisRepository;
import com.jazztech.creditanalysis.presentation.dto.ResponseDto;
import com.jazztech.infrastructure.Factory;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CreditAnalysisSearchTest {
    @Spy
    private CreditAnalysisMapperImpl mapperImpl;
    @Mock
    private CreditAnalysisRepository creditAnalysisRepository;
    @InjectMocks
    private CreditAnalysisSearch creditAnalysisSearch;

    @Test
    void should_find_credit_analysis_by_client_id() {
        when(creditAnalysisRepository.findByClientId(UUID.fromString("dd1efc87-a9c1-4f08-8fb7-1cd71d92dd6d")))
                .thenReturn(List.of(Factory.creditAnalysisEntityFactory()));

        List<ResponseDto> responseDtoList = creditAnalysisSearch.byClientId(UUID.fromString("dd1efc87-a9c1-4f08-8fb7-1cd71d92dd6d"));

        assertEquals(1, responseDtoList.size());
    }

    @Test
    void should_find_2_credit_analysis_by_client_cpf() {
        when(creditAnalysisRepository.findByClientCpf("18784272023"))
                .thenReturn(List.of(Factory.creditAnalysisEntityFactory(),
                        Factory.creditAnalysisEntityFactory())
                );

        List<ResponseDto> responseDtoList = creditAnalysisSearch.byCpf("18784272023");

        assertEquals(2, responseDtoList.size());
    }

    @Test
    void should_find_all_the_3_credit_analysis() {
        when(creditAnalysisRepository.findAll())
                .thenReturn(List.of(Factory.creditAnalysisEntityFactory(),
                        Factory.creditAnalysisEntityFactory(),
                        Factory.creditAnalysisEntityFactory())
                );

        List<ResponseDto> responseDtoList = creditAnalysisSearch.all();

        assertEquals(3, responseDtoList.size());
    }

    @Test
    void should_not_find_any_credit_analysis() {
        when(creditAnalysisRepository.findAll())
                .thenReturn(List.of());

        List<ResponseDto> responseDtoList = creditAnalysisSearch.all();

        assertEquals(0, responseDtoList.size());
    }
}
