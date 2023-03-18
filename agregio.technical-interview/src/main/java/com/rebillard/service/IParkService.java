package com.rebillard.service;

import com.rebillard.model.dto.ParkDTO;

public interface IParkService {

  /**
   * Create a park.
   *
   * @return the created park.
   */
  ParkDTO create(ParkDTO parkDTO);

}
