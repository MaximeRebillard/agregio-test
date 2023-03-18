package com.rebillard.service;

import com.rebillard.model.Park;
import com.rebillard.repository.ParkRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class PostgresDbService implements IDbService {

  private final ParkRepository parkRepository;

  @Transactional
  @Override
  public Park create(Park park) {
    parkRepository.persistAndFlush(park);

    return parkRepository.find("name", park.getName()).stream().findFirst().orElseThrow();
  }
}
