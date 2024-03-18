package com.transfer.service;

import com.transfer.dto.CentralBankTransfer;

public interface SendMessageService {

    void sendToTopic(CentralBankTransfer centralBankTransfer);
}
