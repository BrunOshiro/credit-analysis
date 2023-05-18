package com.jazztech.creditanalysis.infrastructure.repository;

import com.jazztech.creditanalysis.infrastructure.repository.entity.Entity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Entity, UUID> {
    List<Entity> findByCpf(String cpf);
}
