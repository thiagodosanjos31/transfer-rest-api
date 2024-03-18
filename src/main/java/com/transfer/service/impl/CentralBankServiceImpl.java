package com.transfer.service.impl;

import com.transfer.client.CentralBankClient;
import com.transfer.dto.CentralBankDto;
import com.transfer.service.CentralBankService;
import com.transfer.service.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CentralBankServiceImpl implements CentralBankService {

    private final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
    private final CentralBankClient centralBankClient;
    private final SendMessageService sendMessageService;


    public CentralBankServiceImpl(CentralBankClient centralBankClient, SendMessageService sendMessageService) {
        this.centralBankClient = centralBankClient;
        this.sendMessageService = sendMessageService;
    }


    public void callCentralBank(CentralBankDto centralBankDto) {
        logger.info("method=callCentralBank, message=Init send message abount transfer between accounts to central bank.");
        try {
            centralBankClient.sendInformationForCentralBank(centralBankDto.toClient());
        } catch (Exception exception) {
            sendMessageService.sendToTopic(centralBankDto.toClient());
            logger.error("Error to send information to Central Bank.");
        }
    }
}
