package com.transfer.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table
public class Balance {
    @PrimaryKey
    private Integer accountId;
    private Double balance;
    private Double dailyLimit;

    public Balance(Integer accountId, Double balance, Double dailyLimit) {
        this.accountId = accountId;
        this.balance = balance;
        this.dailyLimit = dailyLimit;
    }

    public void subtractBalance(Double value) {
        balance -= value;
    }

    public void addBalance(Double value) {
        balance += value;
    }

    public void updateDailyLimit(Double value) {
        dailyLimit += value;
    }
}
