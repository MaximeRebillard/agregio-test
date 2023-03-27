package com.rebillard.repository;

import com.rebillard.model.Capacity;
import com.rebillard.model.RemainingAvailableCapacity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CapacityRepository implements PanacheRepositoryBase<Capacity, UUID> {

  private EntityManager entityManager;

  //ok
  public List<RemainingAvailableCapacity> findRemainingAvailableCapacity(int energyAmount) {
    return entityManager.createQuery(
            "SELECT new RemainingAvailableCapacity (c.id, c.hours - SUM(b.timeAmount), c.hours) "
                + "FROM Block b INNER JOIN Capacity c on b.capacity.id = c.id WHERE c.energyAmount = :energyAmount "
                + "GROUP BY c.id "
                + "HAVING SUM(b.timeAmount) >= 0",
            RemainingAvailableCapacity.class)
        .setParameter("energyAmount", energyAmount)
        .getResultList();
  }
}
