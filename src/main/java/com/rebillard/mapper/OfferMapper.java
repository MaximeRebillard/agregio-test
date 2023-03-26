package com.rebillard.mapper;

import com.rebillard.model.Offer;
import com.rebillard.model.dto.OfferDTO;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class OfferMapper {

  private BlockMapper blockMapper;

  public Offer getDTOFromDto(OfferDTO offerDTO) {
    return Offer.builder()
        .blockList(offerDTO.getBlockList().stream().map(t ->
            blockMapper.getFromDto(t)).collect(Collectors.toList()))
        .market(offerDTO.getMarket())
        .build();
  }

  public OfferDTO getDTOFromDto(Offer offerDTO) {
    return OfferDTO.builder()
        .blockList(offerDTO.getBlockList().stream().map(t ->
            blockMapper.getParkDtoFromPark(t)).collect(Collectors.toList()))
        .market(offerDTO.getMarket())
        .build();
  }
}
