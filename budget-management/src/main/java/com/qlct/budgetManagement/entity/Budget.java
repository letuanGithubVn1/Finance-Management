package com.qlct.budgetManagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.qlct.core.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Budgets")
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="b_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private BigDecimal limit_amount;
    private LocalDate start_date;
    private LocalDate end_date;
    private LocalDateTime created_at;
    @OneToMany(mappedBy="budget")
    @JsonManagedReference
    private List<ExpenseAllocation> expenseAllocations;
}
