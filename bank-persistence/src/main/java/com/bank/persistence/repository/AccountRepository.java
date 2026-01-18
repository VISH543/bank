package com.bank.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.persistence.entity.Account;
public interface AccountRepository extends JpaRepository<Account, Long> {
}
