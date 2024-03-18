package com.transfer.service.impl;

import com.transfer.dto.CentralBankTransfer;
import com.transfer.service.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.net.URI;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private final Logger logger = LoggerFactory.getLogger(SendMessageServiceImpl.class);

    @Value("${queue-url}")
    private String queueUrl;

    @Override
    public void sendToTopic(CentralBankTransfer centralBankTransfer) {
        logger.info("method=sendToTopic, message=Init to send message to topic");
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl + "/000000000000/balance-and-transfer-queue")
            .messageBody("Transfer failed. Data: " +
                "{ accountIdFrom: " + centralBankTransfer.accountFrom() + " " +
                " accountIdTo: " + centralBankTransfer.accountTo() + " " +
                " value: " + centralBankTransfer.transferValue() + " " +
                " timestamp: " + centralBankTransfer.timestamp() + " }")
            .build();

        SqsClient sqsClient = SqsClient
            .builder()
            .endpointOverride(URI.create(queueUrl))
            .build();

        sqsClient.sendMessage(sendMessageRequest);
    }
}
