package com.example.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    @Inject
    EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> hello() {
        return entityManager
                .createQuery("SELECT f.pdbId FROM FastaSequence f ORDER BY f.pdbId ASC", String.class)
                .setMaxResults(100)
                .getResultList();
    }
}
