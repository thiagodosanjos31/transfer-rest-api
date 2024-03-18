package com.transfer.service;

import com.transfer.dto.TransferDTO;

public interface TransferService {

    void transfer(TransferDTO transferDTO) throws InterruptedException;
}
