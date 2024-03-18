package com.transfer.dto;

import lombok.Data;

@Data
public class TransferDTO {
    private String accountFrom;
    private String accountTo;
    private Double value;
}
