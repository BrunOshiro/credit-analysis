package com.jazztech.creditanalysis;

import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisMapperImpl;
import com.jazztech.creditanalysis.infrastructure.repository.CreditAnalysisRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
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

}
