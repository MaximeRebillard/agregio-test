package com.rebillard.model;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.Setter;

@Entity
@Table(name = "capacity")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Capacity {

  @Id
  @GeneratedValue
  private UUID id;
  private int energyAmount;
  private int hours;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "park_id")
  private Park park;
  @OneToMany(mappedBy = "capacity")
  private List<Block> blocks;

}
