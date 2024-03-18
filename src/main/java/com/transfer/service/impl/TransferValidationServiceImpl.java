package com.transfer.service.impl;

import com.transfer.dto.TransferDTO;
import com.transfer.exception.AccountInvalidException;
import com.transfer.exception.TransferValueHigherThanPermitted;
import com.transfer.service.AccountService;
import com.transfer.service.TransferValidationService;
import com.transfer.utils.ProjectConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class TransferValidationServiceImpl implements TransferValidationService {

    private final Logger logger = LoggerFactory.getLogger(TransferValidationServiceImpl.class);
    private final AccountService accountService;

    public TransferValidationServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void validation(TransferDTO transferData) {
        logger.info("method=validation, message=Init validate account active.");
        Boolean isAccountsActive = accountService.isEveryAccountActive(transferData.getAccountFrom(), transferData.getAccountTo());

        if (!isAccountsActive) {
            throw new AccountInvalidException(
                    "Account is invalid to transfer. Please, call your bank.",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (isTransferValueHigherThanPermitted(transferData)) {
            throw new TransferValueHigherThanPermitted(
                    "Transfer value is higher than limited.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private boolean isTransferValueHigherThanPermitted(TransferDTO transferData) {
        return transferData.getValue() > ProjectConstants.TRANSFER_LIMIT;
    }
}
