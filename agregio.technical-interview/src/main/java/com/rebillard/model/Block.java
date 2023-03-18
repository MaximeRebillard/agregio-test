package com.rebillard.model;

import io.smallrye.common.constraint.NotNull;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Block {

  @Id
  @GeneratedValue
  private UUID id;
  private int timeAmount;

  private int energyAmount;


  private Capacity capacity;


}
