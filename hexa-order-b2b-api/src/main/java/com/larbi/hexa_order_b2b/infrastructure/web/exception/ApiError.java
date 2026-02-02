package com.larbi.hexa_order_b2b.infrastructure.web.exception;

import java.time.Instant;

/**
 * Data Transfer Object (DTO) for representing an API error response.
 * This record is used to structure error messages returned by the API,
 * including a code for the error, a descriptive message, and a timestamp of when the error occurred.
 */
public record ApiError(

        /**
         * The error code that identifies the type of error.
         */
        String code,

        /**
         * A detailed message explaining the error.
         */
        String message,

        /**
         * The timestamp when the error occurred.
         */
        Instant timestamp

) {}
