package com.rebillard.service;

import com.rebillard.mapper.ParkMapper;
import com.rebillard.model.Park;
import com.rebillard.model.dto.ParkDTO;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ParkService implements IParkService {

  private final ParkMapper parkMapper;

  private final IDbService dbService;

  @Override
  public ParkDTO create(ParkDTO parkDTO) {
    Park park = parkMapper.getParkFromDto(parkDTO);
    park = dbService.create(park);
    return parkMapper.getParkDtoFromPark(park);

  }
}
