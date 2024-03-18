package com.transfer.service.impl;

import com.transfer.client.AccountClient;
import com.transfer.dto.AccountDTO;
import com.transfer.dto.TransferDTO;
import com.transfer.entity.Balance;
import com.transfer.repository.AccountRepository;
import com.transfer.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.CassandraConnectionFailureException;
import org.springframework.data.cassandra.CassandraInternalException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

        private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
        private final AccountClient accountClient;
        private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountClient accountClient, AccountRepository accountRepository) {
        this.accountClient = accountClient;
        this.accountRepository = accountRepository;
    }

    public Boolean isEveryAccountActive(String accountFrom, String accountTo) {
            List<AccountDTO> accountList = accountClient.getAccounts(accountFrom + "," + accountTo);

            for(AccountDTO account : accountList){
                if(account.getStatus().equals("INOPERATE")) {
                    return false;
                }
            }
            return true;
        }

        public Boolean thereIsBalanceAvailable(String accountFrom, Double value) {
            Double balance = accountRepository.findByAccountId(accountFrom).getBalance();
            return balance - value > 0.0;
        }

        public void updateBalances(TransferDTO transferData) throws InterruptedException {
            Runnable task = () -> {
                Balance balanceFromRollback = updateBalanceFrom(transferData);
                try {
                    updateBalanceTo(transferData, balanceFromRollback);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            Thread.ofVirtual().start(task);
        }

        private Balance updateBalanceFrom(TransferDTO transferData) {
            Balance balanceAccountFrom = accountRepository.findByAccountId(transferData.getAccountFrom());
            Double value = transferData.getValue();
            balanceAccountFrom.subtractBalance(value);
            balanceAccountFrom.updateDailyLimit(value);
            return accountRepository.save(balanceAccountFrom);
        }

        private void updateBalanceTo(TransferDTO transferData, Balance balanceFromRollback) throws InterruptedException {
            Balance balanceAccountTo = accountRepository.findByAccountId(transferData.getAccountTo());
            Double value = transferData.getValue();
            balanceAccountTo.addBalance(value);
            try{
                accountRepository.save(balanceAccountTo);
            }catch (CassandraConnectionFailureException | CassandraInternalException exception){
                log.error("Update failed. AccountFrom: {} AccountTo: {} Value: {}",
                        transferData.getAccountFrom(), transferData.getAccountTo(), transferData.getValue());
                balanceFromRollback.addBalance(value);accountRepository.save(balanceFromRollback);

            }
        }
}
