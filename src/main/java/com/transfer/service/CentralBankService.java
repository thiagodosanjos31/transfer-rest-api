package com.transfer.service;

import com.transfer.dto.CentralBankDto;

public interface CentralBankService {

    void callCentralBank(CentralBankDto centralBankDto) throws InterruptedException;
}
