package com.rebillard.model;

import io.smallrye.common.constraint.NotNull;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Capacity {

  @NotNull
  private UUID id;
  private double energyAmount;
  private Duration duration;
  private OffsetDateTime startingDate;
  private OffsetDateTime endingDate;
  @NotNull
  private Park park;


}
