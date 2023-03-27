package com.rebillard.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemainingAvailableCapacity extends PanacheEntity {

  @Column(name = "capacity_id")
  private UUID capacityId;

  @Column(name = "blocks_time_amount")
  private long sumBlockTimeAmount;

  @Column(name = "capacity_hours")
  private int capacityHours;

}
