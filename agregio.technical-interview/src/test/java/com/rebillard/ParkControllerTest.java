package com.rebillard;

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
import java.time.Duration;
import java.util.List;
import java.util.UUID;
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

  ParkDTO parkDTO;

  @BeforeEach
  void init() {
    parkDTO = ParkDTO.builder()
        .name("name")
        .type(ParkType.WIND)
        .capacityList(
            List.of(CapacityDTO.builder()
                .energyAmount(200)
                .duration(Duration.ofHours(4))
                .build())
        ).build();
  }

  @Test
  void create_shouldReturnCreatedPark() throws JsonProcessingException {
    Mockito.when(parkService.create(any(ParkDTO.class)))
        .thenReturn(parkDTO);

    given()
        .contentType(ContentType.JSON)
        .body(objectMapper.writeValueAsString(parkDTO))
        .when().post("/park")
        .then()
        .statusCode(200)
        .body(is(objectMapper.writeValueAsString(parkDTO)));
  }

  @Test
  void create_shouldThrowError_whenCapacityListIsEmpty() {

  }

}
