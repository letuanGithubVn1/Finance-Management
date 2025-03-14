/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonBackReference
 *  com.qlct.core.entity.Category
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.Table
 */
package com.qlct.budgetmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qlct.budgetmanagement.entity.Budget;
import com.qlct.core.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="expenseallocation")
public class ExpenseAllocation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="e_a_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name="budget_id")
    @JsonBackReference
    private Budget budget;
    @JoinColumn(name="allocated_amount")
    private BigDecimal allocatedAmount;
    private LocalDateTime created_at;

    public Long getId() {
        return this.id;
    }

    public Category getCategory() {
        return this.category;
    }

    public Budget getBudget() {
        return this.budget;
    }

    public BigDecimal getAllocatedAmount() {
        return this.allocatedAmount;
    }

    public LocalDateTime getCreated_at() {
        return this.created_at;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public void setAllocatedAmount(BigDecimal allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExpenseAllocation)) {
            return false;
        }
        ExpenseAllocation other = (ExpenseAllocation)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Category this$category = this.getCategory();
        Category other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) {
            return false;
        }
        Budget this$budget = this.getBudget();
        Budget other$budget = other.getBudget();
        if (this$budget == null ? other$budget != null : !((Object)this$budget).equals(other$budget)) {
            return false;
        }
        BigDecimal this$allocatedAmount = this.getAllocatedAmount();
        BigDecimal other$allocatedAmount = other.getAllocatedAmount();
        if (this$allocatedAmount == null ? other$allocatedAmount != null : !((Object)this$allocatedAmount).equals(other$allocatedAmount)) {
            return false;
        }
        LocalDateTime this$created_at = this.getCreated_at();
        LocalDateTime other$created_at = other.getCreated_at();
        return !(this$created_at == null ? other$created_at != null : !((Object)this$created_at).equals(other$created_at));
    }

    protected boolean canEqual(Object other) {
        return other instanceof ExpenseAllocation;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Category $category = this.getCategory();
        result = result * 59 + ($category == null ? 43 : $category.hashCode());
        Budget $budget = this.getBudget();
        result = result * 59 + ($budget == null ? 43 : ((Object)$budget).hashCode());
        BigDecimal $allocatedAmount = this.getAllocatedAmount();
        result = result * 59 + ($allocatedAmount == null ? 43 : ((Object)$allocatedAmount).hashCode());
        LocalDateTime $created_at = this.getCreated_at();
        result = result * 59 + ($created_at == null ? 43 : ((Object)$created_at).hashCode());
        return result;
    }

    public String toString() {
        return "ExpenseAllocation(id=" + String.valueOf(this.getId()) + ", category=" + String.valueOf(this.getCategory()) + ", budget=" + String.valueOf(this.getBudget()) + ", allocatedAmount=" + String.valueOf(this.getAllocatedAmount()) + ", created_at=" + String.valueOf(this.getCreated_at()) + ")";
    }
}
