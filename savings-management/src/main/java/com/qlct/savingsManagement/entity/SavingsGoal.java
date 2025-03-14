package com.qlct.savingsManagement.entity;

import java.sql.Date;

import com.qlct.core.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class SavingsGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long s_g_id;

    @ManyToOne
    private User user;

    private String goalName;
    private Double targetAmount;
    private Double currentAmount;
    private Date targetDate;
    private String status;
    private Date created_at;
}
