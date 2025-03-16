package com.qlct.budgetManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qlct.core.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="expenseallocation")
@Data
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

 }
