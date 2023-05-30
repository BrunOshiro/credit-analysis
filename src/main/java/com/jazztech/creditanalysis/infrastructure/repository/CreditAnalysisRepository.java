package com.jazztech.creditanalysis.infrastructure.repository;

import com.jazztech.creditanalysis.infrastructure.repository.entity.CreditAnalysisEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAnalysisRepository extends JpaRepository<CreditAnalysisEntity, UUID> {
    List<CreditAnalysisEntity> findByClientCpf(String clientCpf);

    List<CreditAnalysisEntity> findByClientId(UUID clientId);
}
