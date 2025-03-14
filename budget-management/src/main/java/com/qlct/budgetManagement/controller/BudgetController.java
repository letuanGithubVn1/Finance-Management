/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.qlct.budgetmanagement.entity.Budget
 *  com.qlct.budgetmanagement.service.BudgetService
 *  dto.request_DTO$BudgetRequest
 *  dto.request_DTO$ExpenseAllocationRequest
 *  dto.request_DTO$UpdateBudgetRequest
 *  dto.response_DTO$BudgetStatusResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ResponseEntity
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.qlct.budgetmanagement.controller;

import com.qlct.budgetmanagement.entity.Budget;
import com.qlct.budgetmanagement.service.BudgetService;
import dto.request_DTO;
import dto.response_DTO;
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
    public ResponseEntity<String> createBudget(@RequestBody @Validated request_DTO.BudgetRequest request) {
        this.budgetService.createBudget(request);
        return ResponseEntity.ok((Object)"Ng\u00e2n s\u00e1ch \u0111\u00e3 \u0111\u01b0\u1ee3c t\u1ea1o th\u00e0nh c\u00f4ng.");
    }

    @GetMapping
    public List<Budget> getBudgetsByUser(@RequestParam Long userId) {
        return this.budgetService.getBudgetsByUser(userId);
    }

    @GetMapping(value={"/{budgetId}"})
    public ResponseEntity<?> getBudgetById(@RequestParam Long userId, @PathVariable Long budgetId) {
        Budget budgets = this.budgetService.getBudgetById(budgetId, userId);
        return ResponseEntity.ok((Object)budgets);
    }

    @PutMapping(value={"/{budgetId}"})
    public ResponseEntity<?> updateBudget(@PathVariable Long budgetId, @RequestBody request_DTO.UpdateBudgetRequest request) {
        response_DTO.BudgetStatusResponse updatedBudget = this.budgetService.updateBudget(budgetId, request);
        return ResponseEntity.ok((Object)updatedBudget);
    }

    @DeleteMapping(value={"/{budgetId}"})
    public ResponseEntity<?> deleteBudgetOrCategories(@PathVariable Long budgetId, @RequestParam Long userId, @RequestParam(required=false) List<Long> categoryIds) {
        System.out.print(categoryIds);
        response_DTO.BudgetStatusResponse response = this.budgetService.deleteBudgetOrExpenseAllocations(budgetId, userId, categoryIds);
        return ResponseEntity.ok((Object)response);
    }

    @PostMapping(value={"/{budgetId}/expense-allocations"})
    public ResponseEntity<response_DTO.BudgetStatusResponse> addExpenseAllocations(@PathVariable Long budgetId, @RequestParam Long userId, @RequestBody request_DTO.ExpenseAllocationRequest request) {
        response_DTO.BudgetStatusResponse response = this.budgetService.addExpenseAllocations(budgetId, userId, request);
        return ResponseEntity.ok((Object)response);
    }
}
