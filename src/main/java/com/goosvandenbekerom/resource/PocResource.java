package com.goosvandenbekerom.resource;

import com.goosvandenbekerom.domain.Thing;
import com.goosvandenbekerom.repository.PocRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("poc")
public class PocResource {
    private PocRepository repo;
    @Inject
    public PocResource(PocRepository repo) { this.repo = repo; }

    @GET
    public List<Thing> getAll() {
        return repo.getAllThings();
    }

    @GET
    @Path("{id}")
    public Thing getThing(@PathParam("id") long id) {
        return repo.getThing(id);
    }

    @POST
    public Thing postThing(@FormParam("value") String value) {
        return repo.postThing(value);
    }
}
