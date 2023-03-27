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
    int currentRemainingIndex = 0;
    int currentBlockIndex = 0;
    List<Block> blockDTOList = new ArrayList<>();
    List<Block> finalBlockDTOList = blockDTOList;
    BlockDTO block = offerDTO.getBlockList().get(0);
    //find a capacity that match the block energy amount and has suffisant hours (at least equal to block hours)
    List<RemainingAvailableCapacity> remainingAvailableCapacityList =
        capacityRepository.findRemainingAvailableCapacity(block.getEnergyAmount());
    if (!remainingAvailableCapacityList.isEmpty()) {
      RemainingAvailableCapacity r = remainingAvailableCapacityList.get(0);
      int timeLeft = r.getCapacityHours() - (int) r.getSumBlockTimeAmount();
      int currentBlockAmount = block.getTimeAmount();
      int nextBlockAmount;
      while (timeLeft != 0) {
        if (timeLeft >= currentBlockAmount) {
          //fill entirely
          nextBlockAmount = currentBlockAmount;
          timeLeft -= nextBlockAmount;
          //add block to finalBlockDtoList with amount timeLeft (use nextBlockAmount)
          //block time amount =  block time amount - timeLeft
        } else {
          //fill partially
          nextBlockAmount = currentBlockAmount - timeLeft;
          timeLeft = 0;
        }
        //add block
        finalBlockDTOList.add(
            Block.builder()
                .capacity(Capacity.builder().id(r.getCapacityId()).build())
                .timeAmount(nextBlockAmount)
                .energyAmount(block.getEnergyAmount())
                .build()
        );
        if (timeLeft > 0) {
          //update block (go to next block)
          if (offerDTO.getBlockList().size() > currentBlockIndex + 1) {
            block = offerDTO.getBlockList().get(currentBlockIndex + 1);
          }
        }
        if (timeLeft == 0) {
          //update remaning (go to the next)
          if (remainingAvailableCapacityList.size() > currentRemainingIndex + 1) {
            r = remainingAvailableCapacityList.get(currentRemainingIndex + 1);
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
    } else {
      throw new RuntimeException("no matching capacity for the block at index " + currentBlockIndex);
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
