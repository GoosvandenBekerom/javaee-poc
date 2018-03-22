package com.goosvandenbekerom.repository;

import com.goosvandenbekerom.domain.Thing;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class PocRepository {
    @PersistenceContext(unitName = "poc")
    private EntityManager em;

    public Thing getThing(long id) {
        Thing thing = em.find(Thing.class, id);
        if (thing == null) throw new NotFoundException("Thing was not found");
        return thing;
    }

    public Thing postThing(String value) {
        Thing thing = new Thing(value);
        em.persist(thing);
        return thing;
    }

    public List<Thing> getAllThings() {
        return em.createQuery("SELECT t FROM Thing t", Thing.class).getResultList();
    }
}
