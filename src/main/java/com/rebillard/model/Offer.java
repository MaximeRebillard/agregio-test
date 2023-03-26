package com.rebillard.model;

import com.rebillard.model.enums.MarketType;
import com.rebillard.model.enums.OfferStatus;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "offer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

  @Id
  private UUID id;

  private String issuer;

  @Enumerated(EnumType.STRING)
  private MarketType market;

  @Enumerated(EnumType.STRING)
  private OfferStatus status;

  @OneToMany(mappedBy = "offer")
  private List<Block> blockList;


}
