package com.rebillard.service;

import com.rebillard.mapper.ParkMapper;
import com.rebillard.model.Park;
import com.rebillard.model.dto.ParkDTO;
import com.rebillard.model.enums.MarketType;
import com.rebillard.repository.ParkRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ParkService implements IParkService {

  private final ParkMapper parkMapper;

  private final IDbService dbService;

  private final ParkRepository parkRepository;

  @Override
  public ParkDTO create(ParkDTO parkDTO) {
    Park park = parkMapper.getParkFromDto(parkDTO);
    Park park2 = dbService.create(park);
    return parkMapper.getParkDtoFromPark(park2);
  }

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
