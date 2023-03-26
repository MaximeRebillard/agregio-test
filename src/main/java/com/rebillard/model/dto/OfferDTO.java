package com.rebillard.model.dto;

import com.rebillard.model.enums.MarketType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfferDTO {

  private MarketType market;

  private List<BlockDTO> blockList;

}
