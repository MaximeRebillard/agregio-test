package com.rebillard.service;

import com.rebillard.mapper.ParkMapper;
import com.rebillard.model.Park;
import com.rebillard.model.dto.ParkDTO;
import com.rebillard.model.enums.MarketType;
import com.rebillard.repository.CapacityRepository;
import com.rebillard.repository.ParkRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ParkService implements IParkService {

  private final ParkMapper parkMapper;

  private final ParkRepository parkRepository;

  private final CapacityRepository capacityRepository;

  @Transactional
  public ParkDTO create(ParkDTO parkDTO) {
    Park park = parkMapper.getParkFromDto(parkDTO);
    parkRepository.find("name", park.getName()).firstResult();
    parkRepository.persistAndFlush(park);
    capacityRepository.persist(park.getCapacityList().stream().map(
        t -> {
          t.setPark(park);
          return t;
        }).collect(Collectors.toList()));
    Park toto = parkRepository.find("name", park.getName()).firstResult();
    return parkMapper.getParkDtoFromPark(toto);
  }

  @Transactional
  public List<ParkDTO> listByMarket(MarketType marketType) {
    return parkRepository
        .find("Select p from Capacity c join Park p on c.park = p.id "
            + "join Block b on b.capacity = c.id "
            + "join Offer o on b.offer = o.id where o.market = ?1", marketType)
            .list()
        .stream().map(park -> parkMapper.getParkDtoFromPark(park))
        .collect(Collectors.toList());


  }
}
