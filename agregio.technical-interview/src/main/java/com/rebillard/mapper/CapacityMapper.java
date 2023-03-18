package com.rebillard.mapper;

import com.rebillard.model.Capacity;
import com.rebillard.model.Park;
import com.rebillard.model.dto.CapacityDTO;
import com.rebillard.model.dto.ParkDTO;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapacityMapper {

  public Park getFromDto(CapacityDTO capacityDTO) {
    return null;

  }

  public ParkDTO getParkDtoFromPark(Park park) {
    return ParkDTO.builder()
        .name(park.getName())
        .type(park.getType())
        .build();

  }

}
