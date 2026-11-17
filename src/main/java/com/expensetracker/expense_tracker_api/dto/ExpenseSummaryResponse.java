package com.expensetracker.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.expensetracker.expense_tracker_api.model.Category;

/**
 * DTO (Record) encapsulating monthly spending metrics and category-level aggregation.
 */
public record ExpenseSummaryResponse(
    BigDecimal totalSpent,
    int totalTransactions,
    Map<Category, BigDecimal> categoryBreakdown
) {
    
}
