package com.rebillard.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rebillard.model.enums.ParkType;
import io.smallrye.common.constraint.NotNull;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkDTO {

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private UUID id;

  @NotEmpty
  private String name;

  @NotNull
  private ParkType type;

  @NotNull
  @NotEmpty
  private List<CapacityDTO> capacityList;

}
