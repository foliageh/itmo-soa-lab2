package com.foliageh.itmosoalab2.api.error;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
public class ErrorResponse {
    OffsetDateTime timestamp;
    String message;
    List<String> errors;
}


