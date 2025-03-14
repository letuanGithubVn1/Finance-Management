package com.qlct.core.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // Nhớ ánh xạ cùng tên với DB
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id") // ánh xạ DB
    private Long id;

    private String full_name;
    private String email;
    private String password;
    private Integer phone;
    private String address;
    private Date created_at;
}
	