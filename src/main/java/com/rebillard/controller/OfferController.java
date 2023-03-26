package com.rebillard.controller;

import com.rebillard.model.dto.OfferDTO;
import com.rebillard.service.OfferService;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/offer")
@AllArgsConstructor
public class OfferController {

  private final OfferService offerService;

  @POST
  public void create(@Valid OfferDTO offerDTO) {
    offerService.create(offerDTO);
  }

}
