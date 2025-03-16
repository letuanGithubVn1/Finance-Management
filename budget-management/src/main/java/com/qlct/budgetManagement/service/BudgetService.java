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
import dto.request_DTO.BudgetRequest;
import dto.request_DTO.ExpenseAllocationRequest;
import dto.request_DTO.UpdateBudgetRequest;
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
	
	private final String status_reps = "successfully";
	private final String budget_reps = "Ngân sách không tồn tại";
	
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ExpenseAllocationRepository expenseAllocationRepository;

    @Transactional
    public void createBudget(BudgetRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        BigDecimal totalAllocated = request.getExpenseAllocations().stream().map(ExpenseAllocationRequest::getAllocatedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAllocated.compareTo(request.getLimitAmount()) > 0) {
            throw new IllegalArgumentException("Tổng tiền danh mục vượt ngân sách tổng");
        }
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setLimit_amount(request.getLimitAmount());
        budget.setStart_date(request.getStartDate());
        budget.setEnd_date(request.getEndDate());
        budget.setCreated_at(LocalDateTime.now());
        budgetRepository.save(budget);
        
        ArrayList<ExpenseAllocation> allocations = new ArrayList<>();
        for (ExpenseAllocationRequest allocRequest : request.getExpenseAllocations()) {
            Category category = categoryRepository.findById(allocRequest.getCategoryId())
            								.orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
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
        return budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException(budget_reps));
    }

    @Transactional
    public BudgetStatusResponse updateBudget(Long budgetId, UpdateBudgetRequest request) {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow(() -> new RuntimeException(budget_reps));
        budget.setLimit_amount(request.getLimitAmount());
        budget.setStart_date(request.getStartDate());
        budget.setEnd_date(request.getEndDate());
        budgetRepository.save(budget);
        BigDecimal totalAllocated = request.getExpenseAllocations().stream().map(request_DTO.ExpenseAllocationRequest::getAllocatedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAllocated.compareTo(request.getLimitAmount()) > 0) {
            throw new IllegalArgumentException("Tổng ngân sách của các danh mục không được lớn hơn danh sách tổng");
        }
        for (ExpenseAllocationRequest allocation : request.getExpenseAllocations()) {
            ExpenseAllocation existingAllocation = expenseAllocationRepository.findByBudgetIdAndCategoryId(budgetId, allocation.getCategoryId())
            																   .orElse(new ExpenseAllocation());
            existingAllocation.setBudget(budget);
            Category category = categoryRepository.getcategoryById(allocation.getCategoryId());
            existingAllocation.setCategory(category);
            existingAllocation.setAllocatedAmount(allocation.getAllocatedAmount());
            expenseAllocationRepository.save(existingAllocation);
        }
        return new BudgetStatusResponse(status_reps, "Cập nhật thành công!", budgetId);
    }

    // Cần Test lại cách hoạt động
    @Transactional
    public BudgetStatusResponse deleteBudgetOrExpenseAllocations(Long budgetId, Long userId, List<Long> categoryIds) {
        Budget budget = budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException(budget_reps));
        if (categoryIds == null || categoryIds.isEmpty()) {
            expenseAllocationRepository.deleteByBudget(budget);
            budgetRepository.delete(budget);
            BudgetStatusResponse budgetStatusResponse = new BudgetStatusResponse(status_reps, "Ngân sách được xóa thành công", budgetId);
            return budgetStatusResponse;
        }
        expenseAllocationRepository.deleteByBudgetAndCategoryIds(budget, categoryIds);
        BudgetStatusResponse budgetStatusResponse = new BudgetStatusResponse(status_reps, "Đã xóa các danh mục trong ngân sách được xóa", budgetId);
        return budgetStatusResponse;
    }

    @Transactional
    public BudgetStatusResponse addExpenseAllocations(Long budgetId, Long userId, ExpenseAllocationRequest request) {
        Budget budget = budgetRepository.findBudgetByIdAndUserId(budgetId, userId).orElseThrow(() -> new RuntimeException(budget_reps));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        Optional<ExpenseAllocation> existingAllocation = expenseAllocationRepository.findByBudgetIdAndCategoryId(budgetId, request.getCategoryId());
        if (existingAllocation.isPresent()) {
            ExpenseAllocation allocation = existingAllocation.get();
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
        return new BudgetStatusResponse(status_reps, "Thêm danh mục của ngân sách thành công", budgetId);
    }
}
