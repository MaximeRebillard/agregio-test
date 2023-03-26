package com.rebillard.model.dto;

import com.rebillard.model.enums.MarketType;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfferDTO {

  @NotEmpty
  private String issuer;

  @NotNull
  private MarketType market;

  @NotEmpty
  private List<BlockDTO> blockList;

}
