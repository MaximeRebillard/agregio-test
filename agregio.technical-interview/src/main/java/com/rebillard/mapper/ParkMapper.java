package com.rebillard.mapper;

import com.rebillard.model.Park;
import com.rebillard.model.dto.CapacityDTO;
import com.rebillard.model.dto.ParkDTO;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ParkMapper {

  private final CapacityMapper capacityMapper;

  public Park getParkFromDto(ParkDTO parkDTO) {
    return Park.builder()
        .name(parkDTO.getName())
        .type(parkDTO.getType())
        .build();

  }

  public ParkDTO getParkDtoFromPark(Park park) {
    return ParkDTO.builder()
        .name(park.getName())
        .type(park.getType())
        .build();

  }


}
