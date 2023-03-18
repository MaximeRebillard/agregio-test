package com.rebillard.model.dto;

import java.time.Duration;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CapacityDTO {

  private UUID id;

  private double energyAmount;
  private Duration duration;

}
