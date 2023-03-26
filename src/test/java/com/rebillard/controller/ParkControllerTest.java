package com.rebillard.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebillard.model.dto.CapacityDTO;
import com.rebillard.model.dto.ParkDTO;
import com.rebillard.model.enums.ParkType;
import com.rebillard.service.ParkService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class ParkControllerTest {

  @Inject
  ObjectMapper objectMapper;

  @InjectMock
  ParkService parkService;

  ParkDTO park;

  @BeforeEach
  void init() {
    park = ParkDTO.builder()
        .name("name")
        .type(ParkType.WIND)
        .capacityList(
            List.of(CapacityDTO.builder()
                .energyAmount(200)
                .hours(4)
                .build())
        ).build();
  }

  @Test
  void create_shouldReturnCreatedPark() throws JsonProcessingException {
    Mockito.when(parkService.create(any(ParkDTO.class)))
        .thenReturn(park);

    given()
        .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsString(park))
        .when().post("/park")
        .then()
        .statusCode(200)
        .body(is(objectMapper.writeValueAsString(park)));
  }

}
