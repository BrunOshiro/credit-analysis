package com.jazztech.creditanalysis.infrastructure.clientsapi;

import com.jazztech.creditanalysis.infrastructure.clientsapi.dto.ClientApiResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "client", url = "http://localhost:8080", configuration = CustomFeignConfiguration.class)
public interface ClientApi {
    @GetMapping("/api/v1.0/clients/{id}")
    ClientApiResponseDto getClientById(@PathVariable("id")UUID clientId);
}
