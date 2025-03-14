package com.qlct.expenseManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qlct.expenseManagement.entity.Transaction;
import com.qlct.expenseManagement.service.TransactionService;

import dto.request_transaction_DTO.transactionRequest;
import dto.response_transaction_DTO.TransactionStatusResponse;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Người dùng tạo transaction
    @PostMapping
    public ResponseEntity<TransactionStatusResponse> createTransaction(@RequestBody transactionRequest transactionRequest, 
    																   @RequestParam Long userId) {
    	TransactionStatusResponse transactionStatusResponse = transactionService.createTransaction(transactionRequest, userId);
    	return ResponseEntity.ok(transactionStatusResponse);
    }

    // Lấy danh sách giao dịch theo userId
    @GetMapping
    public List<Transaction> getTransactionsByUser(@RequestParam Long userId) {
        return transactionService.getTransactionsByUser(userId);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id,
    									 @RequestBody Transaction transactionDetails) {
        return transactionService.updateTransaction(id, transactionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
