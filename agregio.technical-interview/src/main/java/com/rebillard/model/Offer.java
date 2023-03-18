package com.rebillard.model;

import com.rebillard.model.enums.MarketType;
import com.rebillard.model.enums.OfferStatus;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Offer {

  private UUID id;
  private OffsetDateTime creationDate; //TODO: useless ?
  private MarketType market;
  private OfferStatus status;


}
