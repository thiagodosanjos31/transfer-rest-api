package com.transfer.service.impl;

import com.transfer.dto.CentralBankDto;
import com.transfer.dto.TransferDTO;
import com.transfer.service.AccountService;
import com.transfer.service.CentralBankService;
import com.transfer.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransferServiceImpl implements TransferService {

    private Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
    private final CentralBankService centralBankService;
    private final AccountService accountService;

    public TransferServiceImpl(CentralBankService centralBankService, AccountService accountService) {
        this.centralBankService = centralBankService;
        this.accountService = accountService;
    }

    @Override
    public void transfer(TransferDTO transferDTO) throws InterruptedException {
        logger.info("method=transfer, message=Init transfer value between accounts");
        if(accountService.thereIsBalanceAvailable(transferDTO.getAccountFrom(), transferDTO.getValue())){
            accountService.updateBalances(transferDTO);
            var centralBankDto = new CentralBankDto(
                    transferDTO.getAccountTo(),
                    transferDTO.getAccountFrom(),
                    transferDTO.getValue(),
                    LocalDateTime.now()
            );
            centralBankService.callCentralBank(centralBankDto);
        }
    }
}
