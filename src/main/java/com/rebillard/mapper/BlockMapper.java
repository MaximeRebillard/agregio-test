package com.rebillard.mapper;

import com.rebillard.model.Block;
import com.rebillard.model.dto.BlockDTO;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlockMapper {

  public Block getFromDto(BlockDTO blockDTO) {
    return Block.builder()
        .energyAmount(blockDTO.getEnergyAmount())
        .timeAmount(blockDTO.getTimeAmount())
        .build();

  }

  public BlockDTO getParkDtoFromPark(Block block) {
    return BlockDTO.builder()
        .energyAmount(block.getEnergyAmount())
        .timeAmount(block.getTimeAmount())
        .build();
  }

}
