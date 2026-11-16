package com.expensetracker.expense_tracker_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * Represents an individual expense record in the database.
 * Mapped to the "expenses" table via JPA.
 */
@Entity
@Table(name="expenses")
public class Expense {

    /**
     * Unique identifier auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    /**
     * Exact monetary amount using BigDecimal to avoid floating-point inaccuracies.
     */
    @Column(nullable=false)
    private BigDecimal amount;

    /**
     * Expense category stored as a readable string in the database.
     */
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name="expense_date", nullable=false)
    private LocalDate expenseDate;

    /**
     * Creation timestamp set automatically before persisting.
     */
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Default constructor required by JPA for entity reflection.
     */
    public Expense() {}

    /**
     * Convenience constructor for creating new expense instances.
     */
    public Expense(String title, BigDecimal amount, Category category, LocalDate expenseDate) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    /**
     * Automatically captures the current timestamp right before the entity is saved.
     */
    @PrePersist 
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ---- Getters and Setters ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public LocalDate getExpenseDate() {
        return expenseDate;
    }
    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
