package com.rebillard.mapper;

import com.rebillard.model.Capacity;
import com.rebillard.model.dto.CapacityDTO;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapacityMapper {

  public Capacity getFromDto(CapacityDTO capacityDTO) {
    return Capacity.builder()
        .energyAmount(capacityDTO.getEnergyAmount())
        .hours(capacityDTO.getHours())
        .build();

  }

  public CapacityDTO getCapacityDtoFromPark(Capacity capacity) {
    return CapacityDTO.builder()
        .hours(capacity.getHours())
        .energyAmount(capacity.getEnergyAmount())
        .build();
  }

}
