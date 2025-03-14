package com.qlct.expenseManagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlct.core.entity.Category;
import com.qlct.core.entity.User;
import com.qlct.core.repository.CategoryRepository;
import com.qlct.core.repository.UserRepository;
import com.qlct.expenseManagement.entity.Transaction;
import com.qlct.expenseManagement.repository.TransactionRepository;

import dto.request_transaction_DTO.transactionRequest;
import dto.response_transaction_DTO.TransactionStatusResponse;
import jakarta.transaction.Transactional;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public TransactionStatusResponse createTransaction(transactionRequest transactionRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(transactionRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setPayment_type(transactionRequest.getPaymentType());
        transaction.setTransaction_date(LocalDateTime.now());
        transaction.setNote(transactionRequest.getNote());
        transaction.setCreated_at(LocalDateTime.now());
        transactionRepository.save(transaction);
        
        TransactionStatusResponse transactionStatusResponse = new TransactionStatusResponse("successfully", "Giao dịch thành công", userId);
        return transactionStatusResponse;
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        return transactionRepository.findById(id).map(transaction -> {
            transaction.setAmount(transactionDetails.getAmount());
            transaction.setPayment_type(transactionDetails.getPayment_type());
            transaction.setTransaction_date(transactionDetails.getTransaction_date());
            transaction.setNote(transactionDetails.getNote());
            return transactionRepository.save(transaction);
        }).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
