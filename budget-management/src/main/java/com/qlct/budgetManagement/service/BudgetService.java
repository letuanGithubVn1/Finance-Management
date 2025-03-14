/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.qlct.budgetmanagement.entity.Budget
 *  com.qlct.budgetmanagement.entity.ExpenseAllocation
 *  com.qlct.budgetmanagement.repository.BudgetRepository
 *  com.qlct.budgetmanagement.repository.ExpenseAllocationRepository
 *  com.qlct.core.entity.Category
 *  com.qlct.core.entity.User
 *  com.qlct.core.repository.CategoryRepository
 *  com.qlct.core.repository.UserRepository
 *  dto.request_DTO$BudgetRequest
 *  dto.request_DTO$ExpenseAllocationRequest
 *  dto.request_DTO$UpdateBudgetRequest
 *  dto.response_DTO$BudgetStatusResponse
 *  jakarta.transaction.Transactional
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Service
 */
package com.qlct.budgetManagement.service;

import com.qlct.budgetManagement.entity.Budget;
import com.qlct.budgetManagement.entity.ExpenseAllocation;
import com.qlct.budgetManagement.repository.BudgetRepository;
import com.qlct.budgetManagement.repository.ExpenseAllocationRepository;
import com.qlct.core.entity.Category;
import com.qlct.core.entity.User;
import com.qlct.core.repository.CategoryRepository;
import com.qlct.core.repository.UserRepository;
import dto.request_DTO;
import dto.response_DTO;
import dto.response_DTO.BudgetStatusResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ExpenseAllocationRepository expenseAllocationRepository;

    @Transactional
    public void createBudget(request_DTO.BudgetRequest request) {
        User user = (User)userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        BigDecimal totalAllocated = request.getExpenseAllocations().stream().map(request_DTO.ExpenseAllocationRequest::getAllocatedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAllocated.compareTo(request.getLimitAmount()) > 0) {
            throw new RuntimeException("tông tiền danh mục vượt tôngt ngân sách");
        }
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setLimit_amount(request.getLimitAmount());
        budget.setStart_date(request.getStartDate());
        budget.setEnd_date(request.getEndDate());
        budget.setCreated_at(LocalDateTime.now());
        budgetRepository.save(budget);
        ArrayList<ExpenseAllocation> allocations = new ArrayList<ExpenseAllocation>();
        for (request_DTO.ExpenseAllocationRequest allocRequest : request.getExpenseAllocations()) {
            Category category = (Category)categoryRepository.findById(allocRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Danh m\u1ee5c kh\u00f4ng t\u1ed3n t\u1ea1i"));
            ExpenseAllocation allocation = new ExpenseAllocation();
            allocation.setBudget(budget);
            allocation.setCategory(category);
            allocation.setAllocatedAmount(allocRequest.getAllocatedAmount());
            allocation.setCreated_at(LocalDateTime.now());
            allocations.add(allocation);
        }
        expenseAllocationRepository.saveAll(allocations);
    }

    public List<Budget> getBudgetsByUser(Long userId) {
        return budgetRepository.findBudgetByUser_Id(userId);
    }

    public Budget getBudgetById(Long budgetId, Long userId) {
        return (Budget)budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    @Transactional
    public response_DTO.BudgetStatusResponse updateBudget(Long budgetId, request_DTO.UpdateBudgetRequest request) {
        Budget budget = (Budget)budgetRepository.findById(budgetId).orElseThrow(() -> new RuntimeException("Ng\u00e2n s\u00e1ch kh\u00f4ng t\u1ed3n t\u1ea1i"));
        budget.setLimit_amount(request.getLimitAmount());
        budget.setStart_date(request.getStartDate());
        budget.setEnd_date(request.getEndDate());
        budgetRepository.save(budget);
        BigDecimal totalAllocated = request.getExpenseAllocations().stream().map(request_DTO.ExpenseAllocationRequest::getAllocatedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAllocated.compareTo(request.getLimitAmount()) > 0) {
            throw new IllegalArgumentException("T\u1ed5ng ng\u00e2n s\u00e1ch danh m\u1ee5c kh\u00f4ng th\u1ec3 l\u1edbn h\u01a1n ng\u00e2n s\u00e1ch t\u1ed5ng");
        }
        for (request_DTO.ExpenseAllocationRequest allocation : request.getExpenseAllocations()) {
            ExpenseAllocation existingAllocation = expenseAllocationRepository.findByBudgetIdAndCategoryId(budgetId, allocation.getCategoryId()).orElse(new ExpenseAllocation());
            existingAllocation.setBudget(budget);
            Category category = categoryRepository.getcategoryById(allocation.getCategoryId());
            existingAllocation.setCategory(category);
            existingAllocation.setAllocatedAmount(allocation.getAllocatedAmount());
            expenseAllocationRepository.save(existingAllocation);
        }
        response_DTO.BudgetStatusResponse budgetStatusResponse = new response_DTO.BudgetStatusResponse("Successfully", "C\u1eadp nh\u1eadt th\u00e0nh c\u00f4ng", budgetId);
        return budgetStatusResponse;
    }

    @Transactional
    public response_DTO.BudgetStatusResponse deleteBudgetOrExpenseAllocations(Long budgetId, Long userId, List<Long> categoryIds) {
        Budget budget = (Budget)budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException("Ng\u00e2n s\u00e1ch kh\u00f4ng \u0111\u01b0\u1ee3c t\u00ecm th\u1ea5y"));
        if (categoryIds == null || categoryIds.isEmpty()) {
            expenseAllocationRepository.deleteByBudget(budget);
            budgetRepository.delete(budget);
            response_DTO.BudgetStatusResponse budgetStatusResponse = new response_DTO.BudgetStatusResponse("Successfully", "Ng\u00e2n s\u00e1ch \u0111\u00e3 \u0111\u01b0\u1ee3c x\u00f3a", budgetId);
            return budgetStatusResponse;
        }
        expenseAllocationRepository.deleteByBudgetAndCategoryIds(budget, categoryIds);
        response_DTO.BudgetStatusResponse budgetStatusResponse = new response_DTO.BudgetStatusResponse("Successfully", "\u0110\u00e3 x\u00f3a danh m\u1ee5c c\u1ee7a ng\u00e2n s\u00e1ch", budgetId);
        return budgetStatusResponse;
    }

    @Transactional
    public BudgetStatusResponse addExpenseAllocations(Long budgetId, Long userId, request_DTO.ExpenseAllocationRequest request) {
        Budget budget = budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException("Ng\u00e2n s\u00e1ch kh\u00f4ng \u0111\u01b0\u1ee3c t\u00ecm th\u1ea5y"));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Danh m\u1ee5c kh\u00f4ng t\u1ed3n t\u1ea1i"));
        Optional existingAllocation = expenseAllocationRepository.findByBudgetIdAndCategoryId(budgetId, request.getCategoryId());
        if (existingAllocation.isPresent()) {
            ExpenseAllocation allocation = (ExpenseAllocation)existingAllocation.get();
            allocation.setAllocatedAmount(request.getAllocatedAmount());
            allocation.setCreated_at(LocalDateTime.now());
            expenseAllocationRepository.save(allocation);
        } else {
            ExpenseAllocation newAllocation = new ExpenseAllocation();
            newAllocation.setCategory(category);
            newAllocation.setBudget(budget);
            newAllocation.setAllocatedAmount(request.getAllocatedAmount());
            newAllocation.setCreated_at(LocalDateTime.now());
            expenseAllocationRepository.save(newAllocation);
        }
        return new response_DTO.BudgetStatusResponse("Successfully", "\u0110\u00e3 c\u00f3 danh m\u1ee5c c\u1ee7a ng\u00e2n s\u00e1ch", budgetId);
    }
}
