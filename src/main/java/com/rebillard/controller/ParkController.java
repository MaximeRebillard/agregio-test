package com.rebillard.controller;

import com.rebillard.model.dto.ParkDTO;
import com.rebillard.model.enums.MarketType;
import com.rebillard.service.ParkService;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import lombok.AllArgsConstructor;

@Path("/park")
@AllArgsConstructor
public class ParkController {

  private final ParkService parkService;

  @POST
  public ParkDTO create(@Valid ParkDTO parkDTO) {
    return parkService.create(parkDTO);
  }

  @GET
  @Path("/{market}")
  public List<ParkDTO> listByMarket(@PathParam("market") MarketType marketType) {
    return parkService.listByMarket(marketType);

  }
}
