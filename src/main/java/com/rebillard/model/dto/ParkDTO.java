package com.rebillard.model.dto;

import com.rebillard.model.enums.ParkType;
import io.smallrye.common.constraint.NotNull;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkDTO {

  @NotEmpty
  private String name;

  @NotNull
  private ParkType type;

  @NotNull
  @NotEmpty
  private List<CapacityDTO> capacityList;

}
