package com.rebillard.model.dto;

import io.smallrye.common.constraint.NotNull;
import java.time.Duration;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CapacityDTO {
  @NotNull
  private UUID id;
  private double energyAmount;
  private Duration duration;

}
