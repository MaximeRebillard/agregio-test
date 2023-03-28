package com.rebillard.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.rebillard.model.Capacity;
import com.rebillard.model.Park;
import com.rebillard.model.dto.CapacityDTO;
import com.rebillard.model.dto.ParkDTO;
import com.rebillard.model.enums.ParkType;
import com.rebillard.repository.CapacityRepository;
import com.rebillard.repository.ParkRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class ParkServiceTest {

  @Inject
  ParkService parkService;

  @InjectMock
  ParkRepository parkRepository;
  @InjectMock
  CapacityRepository capacityRepository;

  @Test
  void create_shouldReturnCreatedPark() {
    String name = "name";
    ParkDTO park = ParkDTO.builder()
        .name("name")
        .type(ParkType.WIND)
        .capacityList(
            List.of(CapacityDTO.builder()
                .energyAmount(200)
                .build())
        ).build();

    doNothing().when(parkRepository).persistAndFlush(any(Park.class));
    doNothing().when(capacityRepository).persistAndFlush(any(Capacity.class));

    PanacheQuery<Park> panacheQueryParkMock = (PanacheQuery<Park>) Mockito.mock((PanacheQuery.class));
    when(panacheQueryParkMock.firstResult()).thenReturn(park);
    when(parkRepository.find("name", park.getName()))
        .thenReturn(panacheQueryParkMock);

    ParkDTO result = parkService.create(
        ParkDTO.builder()
            .name("name")
            .type(ParkType.WIND)
            .capacityList(List.of())
            .build()
    );
    Assertions.assertEquals(ParkType.WIND,result.getType());
    Assertions.assertEquals(name,result.getName());
    Assertions.assertEquals(1,result.getCapacityList().size());
  }


}
