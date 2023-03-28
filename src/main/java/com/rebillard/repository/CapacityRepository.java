package com.rebillard.repository;

import com.rebillard.model.Capacity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CapacityRepository implements PanacheRepositoryBase<Capacity, UUID> {


}
