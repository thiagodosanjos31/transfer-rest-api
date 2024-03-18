package com.transfer.service;

import com.transfer.dto.TransferDTO;

public interface AccountService {

    Boolean isEveryAccountActive(String accountFrom, String accountTo);
    Boolean thereIsBalanceAvailable(String accountFrom, Double value);
    void updateBalances(TransferDTO transferData) throws InterruptedException;
}
