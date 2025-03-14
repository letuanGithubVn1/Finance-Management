/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonManagedReference
 *  com.qlct.core.entity.User
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.OneToMany
 *  jakarta.persistence.Table
 */
package com.qlct.budgetmanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.qlct.budgetmanagement.entity.ExpenseAllocation;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Budgets")
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

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public BigDecimal getLimit_amount() {
        return this.limit_amount;
    }

    public LocalDate getStart_date() {
        return this.start_date;
    }

    public LocalDate getEnd_date() {
        return this.end_date;
    }

    public LocalDateTime getCreated_at() {
        return this.created_at;
    }

    public List<ExpenseAllocation> getExpenseAllocations() {
        return this.expenseAllocations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLimit_amount(BigDecimal limit_amount) {
        this.limit_amount = limit_amount;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setExpenseAllocations(List<ExpenseAllocation> expenseAllocations) {
        this.expenseAllocations = expenseAllocations;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Budget)) {
            return false;
        }
        Budget other = (Budget)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Long this$id = this.getId();
        Long other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        User this$user = this.getUser();
        User other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) {
            return false;
        }
        BigDecimal this$limit_amount = this.getLimit_amount();
        BigDecimal other$limit_amount = other.getLimit_amount();
        if (this$limit_amount == null ? other$limit_amount != null : !((Object)this$limit_amount).equals(other$limit_amount)) {
            return false;
        }
        LocalDate this$start_date = this.getStart_date();
        LocalDate other$start_date = other.getStart_date();
        if (this$start_date == null ? other$start_date != null : !((Object)this$start_date).equals(other$start_date)) {
            return false;
        }
        LocalDate this$end_date = this.getEnd_date();
        LocalDate other$end_date = other.getEnd_date();
        if (this$end_date == null ? other$end_date != null : !((Object)this$end_date).equals(other$end_date)) {
            return false;
        }
        LocalDateTime this$created_at = this.getCreated_at();
        LocalDateTime other$created_at = other.getCreated_at();
        if (this$created_at == null ? other$created_at != null : !((Object)this$created_at).equals(other$created_at)) {
            return false;
        }
        List<ExpenseAllocation> this$expenseAllocations = this.getExpenseAllocations();
        List<ExpenseAllocation> other$expenseAllocations = other.getExpenseAllocations();
        return !(this$expenseAllocations == null ? other$expenseAllocations != null : !((Object)this$expenseAllocations).equals(other$expenseAllocations));
    }

    protected boolean canEqual(Object other) {
        return other instanceof Budget;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Long $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        User $user = this.getUser();
        result = result * 59 + ($user == null ? 43 : $user.hashCode());
        BigDecimal $limit_amount = this.getLimit_amount();
        result = result * 59 + ($limit_amount == null ? 43 : ((Object)$limit_amount).hashCode());
        LocalDate $start_date = this.getStart_date();
        result = result * 59 + ($start_date == null ? 43 : ((Object)$start_date).hashCode());
        LocalDate $end_date = this.getEnd_date();
        result = result * 59 + ($end_date == null ? 43 : ((Object)$end_date).hashCode());
        LocalDateTime $created_at = this.getCreated_at();
        result = result * 59 + ($created_at == null ? 43 : ((Object)$created_at).hashCode());
        List<ExpenseAllocation> $expenseAllocations = this.getExpenseAllocations();
        result = result * 59 + ($expenseAllocations == null ? 43 : ((Object)$expenseAllocations).hashCode());
        return result;
    }

    public String toString() {
        return "Budget(id=" + String.valueOf(this.getId()) + ", user=" + String.valueOf(this.getUser()) + ", limit_amount=" + String.valueOf(this.getLimit_amount()) + ", start_date=" + String.valueOf(this.getStart_date()) + ", end_date=" + String.valueOf(this.getEnd_date()) + ", created_at=" + String.valueOf(this.getCreated_at()) + ", expenseAllocations=" + String.valueOf(this.getExpenseAllocations()) + ")";
    }
}
