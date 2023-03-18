package com.rebillard.controller;

import com.rebillard.model.dto.ParkDTO;
import com.rebillard.service.ParkService;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import lombok.AllArgsConstructor;

@Path("/park")
@AllArgsConstructor
public class ParkController {

  private final ParkService parkService;

  @POST
  public ParkDTO create(@Valid ParkDTO parkDTO) {
    return parkService.create(parkDTO);
  }
}
