package com.rebillard.service;

import com.rebillard.model.Capacity;
import com.rebillard.model.Park;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FakeDbService {

  private List<Park> parkList;
  private List<Capacity> capacityList;

  FakeDbService() {
    parkList = new ArrayList<>();
    capacityList = new ArrayList<>();
  }


  public Park create(Park park) {
    park.setId(UUID.randomUUID());
    parkList.add(park);
    return park;
  }
}
