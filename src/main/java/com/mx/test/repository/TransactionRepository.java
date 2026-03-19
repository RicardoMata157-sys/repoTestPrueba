package com.mx.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.test.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
