package com.rebillard.model;

import com.rebillard.model.enums.MarketType;
import com.rebillard.model.enums.OfferStatus;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "offer")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

  @Id
  @GeneratedValue
  private UUID id;
  private MarketType market;
  private OfferStatus status;

  @OneToMany(mappedBy = "id")
  private List<Block> blockList;

}
