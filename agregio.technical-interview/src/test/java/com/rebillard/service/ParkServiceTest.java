package com.rebillard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.rebillard.model.Capacity;
import com.rebillard.model.Park;
import com.rebillard.model.enums.ParkType;
import com.rebillard.repository.CapacityRepository;
import com.rebillard.repository.ParkRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class ParkServiceTest {

  @Inject
  PostgresDbService postgresDbService;

  @InjectMock
  ParkRepository parkRepository;
  @InjectMock
  CapacityRepository capacityRepository;

  @Test
  void create() {

    Park park = Park.builder()
        .id(UUID.randomUUID())
        .name("name")
        .type(ParkType.WIND)
        .capacityList(
            List.of(Capacity.builder()
                .energyAmount(200)
                .build())
        ).build();

    doNothing().when(parkRepository).persistAndFlush(any(Park.class));
    doNothing().when(capacityRepository).persistAndFlush(any(Capacity.class));

    PanacheQuery<Park> panacheQueryParkMock = (PanacheQuery<Park>) Mockito.mock((PanacheQuery.class));
    when(panacheQueryParkMock.firstResult()).thenReturn(park);
    when(parkRepository.find("name", park.getName()))
        .thenReturn(panacheQueryParkMock);

    Park result = postgresDbService.create(park);
    assertEquals(park, result);
  }


}
