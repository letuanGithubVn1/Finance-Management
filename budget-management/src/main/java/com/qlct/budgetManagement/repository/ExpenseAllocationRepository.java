/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.qlct.budgetmanagement.entity.Budget
 *  com.qlct.budgetmanagement.entity.ExpenseAllocation
 *  jakarta.transaction.Transactional
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.Modifying
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.query.Param
 *  org.springframework.stereotype.Repository
 */
package com.qlct.budgetmanagement.repository;

import com.qlct.budgetmanagement.entity.Budget;
import com.qlct.budgetmanagement.entity.ExpenseAllocation;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseAllocationRepository
extends JpaRepository<ExpenseAllocation, Long> {
    public Optional<ExpenseAllocation> findByBudgetIdAndCategoryId(Long var1, Long var2);

    public void deleteByBudget(Budget var1);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM ExpenseAllocation ea WHERE ea.budget = :budget AND ea.category.id IN :categoryIds")
    public void deleteByBudgetAndCategoryIds(@Param(value="budget") Budget var1, @Param(value="categoryIds") List<Long> var2);
}
