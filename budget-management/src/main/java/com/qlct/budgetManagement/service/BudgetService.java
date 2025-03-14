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
package com.qlct.budgetmanagement.service;

import com.qlct.budgetmanagement.entity.Budget;
import com.qlct.budgetmanagement.entity.ExpenseAllocation;
import com.qlct.budgetmanagement.repository.BudgetRepository;
import com.qlct.budgetmanagement.repository.ExpenseAllocationRepository;
import com.qlct.core.entity.Category;
import com.qlct.core.entity.User;
import com.qlct.core.repository.CategoryRepository;
import com.qlct.core.repository.UserRepository;
import dto.request_DTO;
import dto.response_DTO;
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
        User user = (User)this.userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Ng\u01b0\u1eddi d\u00f9ng kh\u00f4ng t\u1ed3n t\u1ea1i"));
        BigDecimal totalAllocated = request.getExpenseAllocations().stream().map(request_DTO.ExpenseAllocationRequest::getAllocatedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAllocated.compareTo(request.getLimitAmount()) > 0) {
            throw new RuntimeException("T\u1ed5ng s\u1ed1 ti\u1ec1n ph\u00e2n b\u1ed5 v\u01b0\u1ee3t qu\u00e1 ng\u00e2n s\u00e1ch cho ph\u00e9p");
        }
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setLimit_amount(request.getLimitAmount());
        budget.setStart_date(request.getStartDate());
        budget.setEnd_date(request.getEndDate());
        budget.setCreated_at(LocalDateTime.now());
        this.budgetRepository.save((Object)budget);
        ArrayList<ExpenseAllocation> allocations = new ArrayList<ExpenseAllocation>();
        for (request_DTO.ExpenseAllocationRequest allocRequest : request.getExpenseAllocations()) {
            Category category = (Category)this.categoryRepository.findById((Object)allocRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Danh m\u1ee5c kh\u00f4ng t\u1ed3n t\u1ea1i"));
            ExpenseAllocation allocation = new ExpenseAllocation();
            allocation.setBudget(budget);
            allocation.setCategory(category);
            allocation.setAllocatedAmount(allocRequest.getAllocatedAmount());
            allocation.setCreated_at(LocalDateTime.now());
            allocations.add(allocation);
        }
        this.expenseAllocationRepository.saveAll(allocations);
    }

    public List<Budget> getBudgetsByUser(Long userId) {
        List budgets = this.budgetRepository.findBudgetByUser_Id(userId);
        return budgets;
    }

    public Budget getBudgetById(Long budgetId, Long userId) {
        return (Budget)this.budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    @Transactional
    public response_DTO.BudgetStatusResponse updateBudget(Long budgetId, request_DTO.UpdateBudgetRequest request) {
        Budget budget = (Budget)this.budgetRepository.findById((Object)budgetId).orElseThrow(() -> new RuntimeException("Ng\u00e2n s\u00e1ch kh\u00f4ng t\u1ed3n t\u1ea1i"));
        budget.setLimit_amount(request.getLimitAmount());
        budget.setStart_date(request.getStartDate());
        budget.setEnd_date(request.getEndDate());
        this.budgetRepository.save((Object)budget);
        BigDecimal totalAllocated = request.getExpenseAllocations().stream().map(request_DTO.ExpenseAllocationRequest::getAllocatedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAllocated.compareTo(request.getLimitAmount()) > 0) {
            throw new IllegalArgumentException("T\u1ed5ng ng\u00e2n s\u00e1ch danh m\u1ee5c kh\u00f4ng th\u1ec3 l\u1edbn h\u01a1n ng\u00e2n s\u00e1ch t\u1ed5ng");
        }
        for (request_DTO.ExpenseAllocationRequest allocation : request.getExpenseAllocations()) {
            ExpenseAllocation existingAllocation = this.expenseAllocationRepository.findByBudgetIdAndCategoryId(budgetId, allocation.getCategoryId()).orElse(new ExpenseAllocation());
            existingAllocation.setBudget(budget);
            Category category = this.categoryRepository.getcategoryById(allocation.getCategoryId());
            existingAllocation.setCategory(category);
            existingAllocation.setAllocatedAmount(allocation.getAllocatedAmount());
            this.expenseAllocationRepository.save((Object)existingAllocation);
        }
        response_DTO.BudgetStatusResponse budgetStatusResponse = new response_DTO.BudgetStatusResponse("Successfully", "C\u1eadp nh\u1eadt th\u00e0nh c\u00f4ng", budgetId);
        return budgetStatusResponse;
    }

    @Transactional
    public response_DTO.BudgetStatusResponse deleteBudgetOrExpenseAllocations(Long budgetId, Long userId, List<Long> categoryIds) {
        Budget budget = (Budget)this.budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException("Ng\u00e2n s\u00e1ch kh\u00f4ng \u0111\u01b0\u1ee3c t\u00ecm th\u1ea5y"));
        if (categoryIds == null || categoryIds.isEmpty()) {
            this.expenseAllocationRepository.deleteByBudget(budget);
            this.budgetRepository.delete((Object)budget);
            response_DTO.BudgetStatusResponse budgetStatusResponse = new response_DTO.BudgetStatusResponse("Successfully", "Ng\u00e2n s\u00e1ch \u0111\u00e3 \u0111\u01b0\u1ee3c x\u00f3a", budgetId);
            return budgetStatusResponse;
        }
        this.expenseAllocationRepository.deleteByBudgetAndCategoryIds(budget, categoryIds);
        response_DTO.BudgetStatusResponse budgetStatusResponse = new response_DTO.BudgetStatusResponse("Successfully", "\u0110\u00e3 x\u00f3a danh m\u1ee5c c\u1ee7a ng\u00e2n s\u00e1ch", budgetId);
        return budgetStatusResponse;
    }

    @Transactional
    public response_DTO.BudgetStatusResponse addExpenseAllocations(Long budgetId, Long userId, request_DTO.ExpenseAllocationRequest request) {
        Budget budget = (Budget)this.budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException("Ng\u00e2n s\u00e1ch kh\u00f4ng \u0111\u01b0\u1ee3c t\u00ecm th\u1ea5y"));
        Category category = (Category)this.categoryRepository.findById((Object)request.getCategoryId()).orElseThrow(() -> new RuntimeException("Danh m\u1ee5c kh\u00f4ng t\u1ed3n t\u1ea1i"));
        Optional existingAllocation = this.expenseAllocationRepository.findByBudgetIdAndCategoryId(budgetId, request.getCategoryId());
        if (existingAllocation.isPresent()) {
            ExpenseAllocation allocation = (ExpenseAllocation)existingAllocation.get();
            allocation.setAllocatedAmount(request.getAllocatedAmount());
            allocation.setCreated_at(LocalDateTime.now());
            this.expenseAllocationRepository.save((Object)allocation);
        } else {
            ExpenseAllocation newAllocation = new ExpenseAllocation();
            newAllocation.setCategory(category);
            newAllocation.setBudget(budget);
            newAllocation.setAllocatedAmount(request.getAllocatedAmount());
            newAllocation.setCreated_at(LocalDateTime.now());
            this.expenseAllocationRepository.save((Object)newAllocation);
        }
        return new response_DTO.BudgetStatusResponse("Successfully", "\u0110\u00e3 c\u00f3 danh m\u1ee5c c\u1ee7a ng\u00e2n s\u00e1ch", budgetId);
    }
}
