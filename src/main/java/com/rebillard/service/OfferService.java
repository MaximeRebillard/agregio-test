package com.rebillard.service;

import com.rebillard.mapper.BlockMapper;
import com.rebillard.mapper.OfferMapper;
import com.rebillard.model.Block;
import com.rebillard.model.Capacity;
import com.rebillard.model.Offer;
import com.rebillard.model.dto.OfferDTO;
import com.rebillard.model.enums.OfferStatus;
import com.rebillard.repository.BlockRepository;
import com.rebillard.repository.CapacityRepository;
import com.rebillard.repository.OfferRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class OfferService implements IOfferService {

  private BlockMapper blockMapper;

  private OfferMapper offerMapper;
  private OfferRepository offerRepository;
  private CapacityRepository capacityRepository;
  private BlockRepository blockRepository;

  @Transactional
  @Override
  public OfferDTO create(OfferDTO offerDTO) {
    List<Block> blockDTOList = new ArrayList<>();
    List<Block> finalBlockDTOList = blockDTOList;
    offerDTO.getBlockList().forEach(block -> {
      //find a park that match the energy amount
      Optional<Capacity> capacityOptional =
          capacityRepository.find("energyAmount", block.getEnergyAmount()).firstResultOptional();
      if (capacityOptional.isPresent()) {
        Capacity c = capacityOptional.get();
        //check already emitted block
        int sumHoursEmittedFromCapacity = capacityOptional.get().getBlocks().stream()
            .mapToInt(Block::getTimeAmount)
            .sum();
        //check can emit new block
        if (c.getHours() - sumHoursEmittedFromCapacity >= block.getTimeAmount()) {
          Block b = blockMapper.getFromDto(block);
          b.setCapacity(c);
          finalBlockDTOList.add(b);
        }
      }
    });
    if (offerDTO.getBlockList().size() != blockDTOList.size()) {
      throw new RuntimeException("can't create the hole offer");
    } else {
      Offer offer = Offer.builder()
          .id(UUID.randomUUID())
          .status(OfferStatus.PENDING)
          .market(offerDTO.getMarket())
          .issuer(offerDTO.getIssuer())
          .build();
      offerRepository.persistAndFlush(offer);
      blockDTOList = finalBlockDTOList.stream().map(t -> {
        t.setOffer(offer);
        return t;
      }).collect(Collectors.toList());
      blockRepository.persist(blockDTOList);
      blockRepository.flush();
    }
    return OfferDTO.builder()
        .market(offerDTO.getMarket())
        .issuer(offerDTO.getIssuer())
        .blockList(blockDTOList.stream()
            .map(t -> blockMapper.getParkDtoFromPark(t)).collect(Collectors.toList()))
        .build();
  }

  public List<OfferDTO> findAllByIssuer(String issuer) {
    return offerRepository.find("issuer = ?1", issuer).list()
        .stream().map(t -> offerMapper.getDTOFromDto(t))
        .collect(Collectors.toList());

  }

}
