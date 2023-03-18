package com.rebillard.model.dto;

import java.time.Duration;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CapacityDTO {

  private double energyAmount;
  private Duration duration;

}
