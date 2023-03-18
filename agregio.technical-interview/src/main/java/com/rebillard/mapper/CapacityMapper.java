package com.rebillard.mapper;

import com.rebillard.model.Capacity;
import com.rebillard.model.dto.CapacityDTO;
import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapacityMapper {

  public Capacity getFromDto(CapacityDTO capacityDTO) {
    return Capacity.builder()
        .duration(capacityDTO.getDuration())
        .energyAmount(capacityDTO.getEnergyAmount())
        .build();

  }

  public CapacityDTO getParkDtoFromPark(Capacity capacity) {
    return CapacityDTO.builder()
        .duration(Duration.ofHours(4))
        .energyAmount(capacity.getEnergyAmount())
        .build();
  }

}
