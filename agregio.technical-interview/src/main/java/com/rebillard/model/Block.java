package com.rebillard.model;

import io.smallrye.common.constraint.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Block {

  @NotNull
  private UUID id;
  private int timeAmount;

  private int energyAmount;

  @NotNull
  private Capacity capacity;


}
