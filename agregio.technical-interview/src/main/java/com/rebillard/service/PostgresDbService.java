package com.rebillard.service;

import com.rebillard.model.Park;
import com.rebillard.repository.CapacityRepository;
import com.rebillard.repository.ParkRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class PostgresDbService implements IDbService {

  private final ParkRepository parkRepository;
  private final CapacityRepository capacityRepository;

  @Transactional
  @Override
  public Park create(Park park) {
    parkRepository.find("name", park.getName()).firstResult();
    parkRepository.persistAndFlush(park);
    capacityRepository.persist(park.getCapacityList().stream().map(
        t -> {
          t.setPark(park);
          return t;
        }
    ));

    return parkRepository.find("name", park.getName()).firstResult();
  }
}
