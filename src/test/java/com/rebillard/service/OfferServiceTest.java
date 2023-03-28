package com.rebillard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.rebillard.model.Block;
import com.rebillard.model.Capacity;
import com.rebillard.model.dto.BlockDTO;
import com.rebillard.model.dto.OfferDTO;
import com.rebillard.model.enums.MarketType;
import com.rebillard.repository.BlockRepository;
import com.rebillard.repository.CapacityRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class OfferServiceTest {

  @Inject
  OfferService offerService;

  @InjectMock
  CapacityRepository capacityRepository;
  @InjectMock
  BlockRepository blockRepository;

  Capacity capacity;

  PanacheQuery<Capacity> panacheQueryCapacityRepoMock;


  @BeforeEach
  void init() {
    UUID capacityId = UUID.randomUUID();
    capacity =
        Capacity.builder()
            .id(capacityId)
            .hours(2)
            .energyAmount(30)
            .blocks(List.of(
                Block.builder()
                    .id(capacityId)
                    .energyAmount(30)
                    .timeAmount(1)
                    .build()
            ))
            .build();
    panacheQueryCapacityRepoMock = (PanacheQuery<Capacity>) Mockito.mock((PanacheQuery.class));
  }

  @Test
  void create_shouldCreateBlockIfParkCapacityCanEmitBlocks() {
    MarketType marketType = MarketType.PRIMARY;
    OfferDTO offerDTO = OfferDTO.builder()
        .blockList(List.of(
            BlockDTO.builder()
                .energyAmount(30)
                .timeAmount(1)
                .build(),
            BlockDTO.builder()
                .energyAmount(30)
                .timeAmount(1)
                .build()))
        .market(marketType)
        .build();

    when(panacheQueryCapacityRepoMock.firstResultOptional())
        .thenReturn(Optional.of(capacity));
    when(capacityRepository.find("energyAmount", 30))
        .thenReturn(panacheQueryCapacityRepoMock);
    doNothing().when(blockRepository).persist(any(Block.class));
    doNothing().when(blockRepository).flush();

    OfferDTO result = offerService.create(offerDTO);

    assertEquals(marketType, result.getMarket());

    assertEquals(2, result.getBlockList().size());
    result.getBlockList().forEach(block -> {
      assertEquals(30, block.getEnergyAmount());
      assertEquals(1, block.getTimeAmount());
    });


  }

  @Test
  void create_shouldNotCreateAnyBlockIfParkCapacityCannotEmitBlocks() {

    when(panacheQueryCapacityRepoMock.firstResultOptional())
        .thenReturn(Optional.of(capacity));
    when(capacityRepository.find("energyAmount", 30))
        .thenReturn(panacheQueryCapacityRepoMock);

    OfferDTO offerDTO =
        OfferDTO.builder()
            .market(MarketType.PRIMARY)
            .blockList(List.of(
                BlockDTO.builder()
                    .energyAmount(30)
                    .timeAmount(1)
                    .build(),
                BlockDTO.builder()
                    .energyAmount(30)
                    .timeAmount(3)
                    .build()
            ))
            .build();
    assertThrows(RuntimeException.class, () -> offerService.create(offerDTO));

  }
}
