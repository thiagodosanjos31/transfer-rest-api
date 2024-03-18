package com.transfer.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private String accountId;
    private String status;

    public AccountDTO(String accountId, String status) {
        this.accountId = accountId;
        this.status = status;
    }
}
