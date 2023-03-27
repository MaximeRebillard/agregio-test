package com.rebillard.model;

import com.rebillard.model.enums.ParkType;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "park")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Park {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @Enumerated(EnumType.STRING)
  private ParkType type;

  @OneToMany(mappedBy = "park")
  private List<Capacity> capacityList;

}
