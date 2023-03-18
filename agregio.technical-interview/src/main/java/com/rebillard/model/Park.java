package com.rebillard.model;

import com.rebillard.model.enums.ParkType;
import io.smallrye.common.constraint.NotNull;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "park")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Park {
  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  private ParkType type;

  @OneToMany(mappedBy = "id")
  private List<Capacity> capacityList;

}
