package com.foliageh.itmosoalab2.api;

import com.foliageh.itmosoalab2.api.dto.FlatDto;
import com.foliageh.itmosoalab2.api.dto.FlatPageResponse;
import com.foliageh.itmosoalab2.api.dto.FlatsFilterDto;
import com.foliageh.itmosoalab2.domain.flat.Flat;
import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.service.FlatService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/flats")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlatResource {
    @Inject
    FlatService service;

    @GET
    @Path("/{id}")
    public Flat getFlat(@PathParam("id") int id) {
        return service.get(id);
    }

    @PUT
    @Path("/{id}")
    public Flat updateFlat(@PathParam("id") int id, @Valid FlatDto dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFlat(@PathParam("id") int id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    public Response createFlat(@Valid FlatDto dto) {
        Flat flat = service.create(dto);
        return Response.created(URI.create("/api/flats/" + flat.getId())).entity(flat).build();
    }

    @POST
    @Path("/filter")
    public FlatPageResponse getFlats(@QueryParam("sortBy") String sortBy,
                                     @QueryParam("sortDirection") String sortDirection,
                                     @DefaultValue("0") @QueryParam("pageNumber") int pageNumber,
                                     @DefaultValue("20") @QueryParam("pageSize") int pageSize,
                                     @Valid FlatsFilterDto filter) {
        return service.filter(filter, sortBy, sortDirection, pageNumber, pageSize);
    }

    @GET
    @Path("/rooms-greater-than/{rooms}")
    public List<Flat> getFlatsWithMoreRooms(@PathParam("rooms") int rooms) {
        return service.findRoomsGreaterThan(rooms);
    }

    @DELETE
    @Path("/by-furnish/{furnish}")
    public Response deleteByFurnish(@PathParam("furnish") Furnish furnish) {
        service.deleteByFurnish(furnish);
        return Response.noContent().build();
    }

    @GET
    @Path("/unique-living-spaces")
    public List<Double> getUniqueLivingSpaces() {
        return service.getUniqueLivingSpaces();
    }

    @POST
    @Path("/unique-living-spaces")
    public Response launchUniqueLivingSpacesJob() {
        service.launchUniqueLivingSpacesJob();
        return Response.noContent().build();
    }

    @DELETE
    @Path("/unique-living-spaces")
    public Response cancelUniqueLivingSpacesJob() {
        service.cancelUniqueLivingSpacesJob();
        return Response.noContent().build();
    }
}


