package com.rebillard.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "block")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Block {

  @Id
  @GeneratedValue
  private UUID id;
  private int timeAmount;

  private int energyAmount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "capacity_id")
  private Capacity capacity;


}
