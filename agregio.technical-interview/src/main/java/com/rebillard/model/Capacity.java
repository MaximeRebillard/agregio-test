package com.rebillard.model;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "capacity")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Capacity {

  @Id
  @GeneratedValue
  private UUID id;
  private double energyAmount;
  private Duration duration;
  @ManyToOne
  @JoinColumn(name = "park_id")
  private Park park;
  @OneToMany(mappedBy = "id")
  private List<Block> blocks;


}
