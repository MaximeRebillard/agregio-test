package com.rebillard.repository;

import com.rebillard.model.Offer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OfferRepository implements PanacheRepositoryBase<Offer, UUID> {

}
