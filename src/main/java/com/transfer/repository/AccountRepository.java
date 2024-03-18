package com.transfer.repository;

import com.transfer.entity.Balance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Balance, Integer> {
    Balance findByAccountId(String accountId);
}
