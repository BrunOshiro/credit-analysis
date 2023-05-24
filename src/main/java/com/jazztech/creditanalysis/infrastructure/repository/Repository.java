package com.jazztech.creditanalysis.infrastructure.repository;

import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Entity, UUID> {
    List<Entity> findByClientCpf(String clientCpf);

    List<Entity> findByClientId(UUID clientId);
}
