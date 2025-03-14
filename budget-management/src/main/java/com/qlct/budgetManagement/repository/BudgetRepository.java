/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.qlct.budgetmanagement.entity.Budget
 *  jakarta.transaction.Transactional
 *  org.springframework.data.jpa.repository.JpaRepository
 *  org.springframework.data.jpa.repository.Modifying
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.query.Param
 *  org.springframework.stereotype.Repository
 */
package com.qlct.budgetManagement.repository;

import com.qlct.budgetManagement.entity.Budget;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository
extends JpaRepository<Budget, Long> {
    public List<Budget> findBudgetByUser_Id(Long var1);

    public Optional<Budget> findBudgetByIdAndUserId(Long var1, Long var2);

    @Query(value="SELECT * FROM budgets WHERE b_id = :budgetId And user_id = :userId", nativeQuery=true)
    public Budget findByIdAndUser_UserId(Long var1, Long var2);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM ExpenseAllocation ea WHERE ea.budget = :budget AND ea.category.id IN :categoryIds")
    public void deleteByBudgetAndCategoryIds(@Param(value="budget") Budget var1, @Param(value="categoryIds") List<Long> var2);
}
