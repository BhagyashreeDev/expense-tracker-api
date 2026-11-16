package com.expensetracker.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.expensetracker.expense_tracker_api.model.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO (Record) representing the incoming payload when creating or updating an expense.
 * Validates constraints before hitting business logic.
 */
public record ExpenseRequest(
    
    @NotBlank(message="Title is required")
    @Size (max=100, message="Title must not exceed 100 characters")
    String title,

    @NotNull(message="Amount is required")
    @Positive(message="Amount must be greater than 0")
    BigDecimal amount,

    @NotNull(message="Category is required")
    Category category,

    @NotNull(message="Expense date is required")
    LocalDate expenseDate
){
}
