package com.rebillard.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CapacityDTO {

  private int energyAmount;
  private int hours;

}
