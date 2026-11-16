package com.expensetracker.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.expensetracker.expense_tracker_api.model.Category;
import com.expensetracker.expense_tracker_api.model.Expense;

/**
 * DTO (Record) representing the response payload sent back to clients.
 * Decouples the internal database entity from API representations.
 */
public record ExpenseResponse(
    Long id,
    String title,
    BigDecimal amount,
    Category category,
    LocalDate expenseDate,
    LocalDateTime createdAt
) {
    public static ExpenseResponse fromEntity(Expense expense) {
        return new ExpenseResponse(
            expense.getId(),
            expense.getTitle(),
            expense.getAmount(),
            expense.getCategory(),
            expense.getExpenseDate(),
            expense.getCreatedAt()
        );
    }
}
