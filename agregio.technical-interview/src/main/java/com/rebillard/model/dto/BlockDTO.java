package com.rebillard.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockDTO {

  private int timeAmount;

  private int energyAmount;

}
