package com.mx.test.controller;

import com.mx.test.dto.ApiResponse;
import com.mx.test.dto.PaymentRequest;
import com.mx.test.dto.TransactionResponse;
import com.mx.test.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService ){
    	this.transactionService  = transactionService;
    	
    }

    @PostMapping("/process")
    public ApiResponse<TransactionResponse> process(@Valid @RequestBody PaymentRequest request) {
        TransactionResponse response = transactionService.processPayment(request);
        return new ApiResponse<>(true, "La transacción se insertó correctamente", response);
    }

    @GetMapping
    public List<TransactionResponse> getAll() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public TransactionResponse getById(@PathVariable Long id) {
        return transactionService.findById(id);
    }
}