package com.rebillard.controller;

import com.rebillard.model.Park;
import com.rebillard.model.dto.ParkDTO;
import com.rebillard.service.ParkService;
import io.smallrye.common.constraint.NotNull;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/park")
@AllArgsConstructor
public class ParkController {

  private final ParkService parkService;

  @POST
  public ParkDTO create(@Valid ParkDTO park) {
    return parkService.create(park);
  }
}
