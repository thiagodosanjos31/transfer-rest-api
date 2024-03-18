package com.transfer.dto;

import java.time.LocalDateTime;

public record CentralBankDto(
        String accountTo,
        String accountFrom,
        Double value,
        LocalDateTime timestamp
) {
    public CentralBankTransfer toClient() {

        return new CentralBankTransfer(
                accountTo, accountFrom, value, timestamp
        );
    }
}
