package com.rebillard.repository;

import com.rebillard.model.Park;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParkRepository implements PanacheRepositoryBase<Park, UUID> {

}
