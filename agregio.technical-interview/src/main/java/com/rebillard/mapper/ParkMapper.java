package com.rebillard.mapper;

import com.rebillard.model.Park;
import com.rebillard.model.dto.ParkDTO;
import java.util.stream.Collectors;
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
        .capacityList(
            parkDTO.getCapacityList().stream().map(capacityMapper::getFromDto)
                .collect(Collectors.toList()))
        .build();

  }

  public ParkDTO getParkDtoFromPark(Park park) {
    return ParkDTO.builder()
        .name(park.getName())
        .type(park.getType())
        .capacityList(park.getCapacityList().stream().map(capacityMapper::getCapacityDtoFromPark)
            .collect(Collectors.toList()))
        .build();

  }


}
