package com.rebillard.service;

import com.rebillard.mapper.BlockMapper;
import com.rebillard.mapper.OfferMapper;
import com.rebillard.model.Block;
import com.rebillard.model.Capacity;
import com.rebillard.model.Offer;
import com.rebillard.model.RemainingAvailableCapacity;
import com.rebillard.model.dto.BlockDTO;
import com.rebillard.model.dto.OfferDTO;
import com.rebillard.model.enums.OfferStatus;
import com.rebillard.repository.BlockRepository;
import com.rebillard.repository.CapacityRepository;
import com.rebillard.repository.OfferRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    //init indexes
    int currentRemainingIndex = 0;

    List<Block> blockDTOList = new ArrayList<>();

    //set block to first index
    for (int blockIndex = 0; blockIndex < offerDTO.getBlockList().size(); blockIndex++) {
      //select first block
      BlockDTO block = offerDTO.getBlockList().get(blockIndex);

      //find a capacity that match the block energy amount and has suffisant hours (at least equal to block hours)
      //+ refresh in loop
      List<RemainingAvailableCapacity> remainingAvailableCapacityList =
          capacityRepository.findRemainingAvailableCapacity(block.getEnergyAmount());

      // si plus de capacité alors impossible de fill le reste des blocks
      if (remainingAvailableCapacityList.isEmpty()) {
        break;
      }
      //select first
      RemainingAvailableCapacity r = remainingAvailableCapacityList.get(currentRemainingIndex);


      int currentBlockAmount = block.getTimeAmount();
      int nextBlockAmount = 0;

      boolean blockFilled = false;
      //while block to fill
      while (!blockFilled) {
        if (r.getTimeLeft() >= currentBlockAmount) {
          //fill entirely
          nextBlockAmount = currentBlockAmount;
          r.setSumBlockTimeAmount(r.getSumBlockTimeAmount() + nextBlockAmount);
          //add block to finalBlockDtoList with amount timeLeft (use nextBlockAmount)
          //block time amount =  block time amount - timeLeft
          blockFilled = true;
        } else {
          //fill partially
          nextBlockAmount = currentBlockAmount - r.getTimeLeft();
          r.setSumBlockTimeAmount(r.getCapacityHours());;
        }
        //add block
        blockDTOList.add(
            Block.builder()
                .capacity(Capacity.builder().id(r.getCapacityId()).build())
                .timeAmount(nextBlockAmount)
                .energyAmount(block.getEnergyAmount())
                .build()
        );
      }

      if (r.getTimeLeft() > 0) {
        //on garde la même capacité et on va sur le prochain block
        //si on est pas au dernier block
        if (offerDTO.getBlockList().size() > blockIndex) {
          block = offerDTO.getBlockList().get(blockIndex);
        }
      }
      //capacité remplie
      if (r.getTimeLeft() == 0) {
        //allez à la capacité suivante
        if (remainingAvailableCapacityList.size() > currentRemainingIndex + 1) {
          currentRemainingIndex++;
          r = remainingAvailableCapacityList.get(currentRemainingIndex);
        }
      }

      //if all block filled then break
            /*
            if(finalBlockDTOList.stream().mapToInt(Block::getEnergyAmount).sum() ==
            offerDTO.getBlockList().stream().mapToInt(BlockDTO::getEnergyAmount).sum()) {
              break;
            }

             */

    }

    if (offerDTO.getBlockList().
        size() != blockDTOList.size()) {
      throw new RuntimeException("can't create the hole offer");
    } else {
      Offer offer = Offer.builder()
          .id(UUID.randomUUID())
          .status(OfferStatus.PENDING)
          .market(offerDTO.getMarket())
          .issuer(offerDTO.getIssuer())
          .build();
      offerRepository.persistAndFlush(offer);
      blockDTOList = blockDTOList.stream().map(t -> {
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
                .map(t -> blockMapper.getParkDtoFromPark(t))
                .collect(Collectors.toList()))
            .build();

  }

  public List<OfferDTO> findAllByIssuer(String issuer) {
    return offerRepository.find("issuer = ?1", issuer).list()
        .stream().map(t -> offerMapper.getDTOFromDto(t))
        .collect(Collectors.toList());
  }

}
