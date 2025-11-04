package com.foliageh.itmosoalab2.api.error;

import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.OffsetDateTime;
import java.util.List;

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {
    @Override
    public Response toResponse(NotAllowedException exception) {
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .message("Не разрешено")
                .errors(List.of(exception.getMessage() == null ? "Метод не разрешён" : exception.getMessage()))
                .build();
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}


