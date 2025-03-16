package com.qlct.budgetManagement.controller;

import com.qlct.budgetManagement.entity.Budget;
import com.qlct.budgetManagement.service.BudgetService;
import dto.request_DTO;
import dto.request_DTO.BudgetRequest;
import dto.response_DTO;
import dto.response_DTO.BudgetStatusResponse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/budgets"})
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetStatusResponse> createBudget(@RequestBody @Validated BudgetRequest request) {
        budgetService.createBudget(request);
        BudgetStatusResponse budgetStatusReps = new BudgetStatusResponse("Successfully", "Tạo ngân sách thành công!", request.getBudgetId());
        return ResponseEntity.ok(budgetStatusReps);
    }

    @GetMapping
    public List<Budget> getBudgetsByUser(@RequestParam Long userId) {
        return budgetService.getBudgetsByUser(userId);
    }

    @GetMapping(value={"/{budgetId}"})
    public ResponseEntity<?> getBudgetById(@RequestParam Long userId, @PathVariable Long budgetId) {
        Budget budgets = budgetService.getBudgetById(budgetId, userId);
        return ResponseEntity.ok((Object)budgets);
    }

    @PutMapping(value={"/{budgetId}"})
    public ResponseEntity<?> updateBudget(@PathVariable Long budgetId, @RequestBody request_DTO.UpdateBudgetRequest request) {
        response_DTO.BudgetStatusResponse updatedBudget = budgetService.updateBudget(budgetId, request);
        return ResponseEntity.ok((Object)updatedBudget);
    }

    @DeleteMapping(value={"/{budgetId}"})
    public ResponseEntity<?> deleteBudgetOrCategories(@PathVariable Long budgetId, @RequestParam Long userId, @RequestParam(required=false) List<Long> categoryIds) {
//        System.out.print(categoryIds);
        response_DTO.BudgetStatusResponse response = budgetService.deleteBudgetOrExpenseAllocations(budgetId, userId, categoryIds);
        return ResponseEntity.ok((Object)response);
    }

    @PostMapping(value={"/{budgetId}/expense-allocations"})
    public ResponseEntity<response_DTO.BudgetStatusResponse> addExpenseAllocations(@PathVariable Long budgetId, @RequestParam Long userId, @RequestBody request_DTO.ExpenseAllocationRequest request) {
        response_DTO.BudgetStatusResponse response = budgetService.addExpenseAllocations(budgetId, userId, request);
        return ResponseEntity.ok(response);
    }
}
