package com.transfer.dto;

import java.time.LocalDateTime;

public record ErrorDto(
        LocalDateTime timestamp,
        String message
) {
}
