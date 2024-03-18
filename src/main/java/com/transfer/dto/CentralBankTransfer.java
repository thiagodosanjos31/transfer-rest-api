package com.transfer.dto;

import java.time.LocalDateTime;

public record CentralBankTransfer(
        String accountTo,
        String accountFrom,
        Double transferValue,
        LocalDateTime timestamp
) {
}
