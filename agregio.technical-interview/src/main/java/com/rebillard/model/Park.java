package com.rebillard.model;

import com.rebillard.model.enums.ParkType;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Park {

  private UUID id;

  private String name;

  private ParkType type;

  private List<Capacity> capacityList;

}
