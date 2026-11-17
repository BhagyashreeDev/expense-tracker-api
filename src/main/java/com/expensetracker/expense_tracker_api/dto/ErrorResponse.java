package com.expensetracker.expense_tracker_api.dto;

import java.time.LocalDateTime;

/**
 * Standardized error payload returned across all API endpoints when exceptions occur.
 */
public record ErrorResponse (
    LocalDateTime timestamp,
    int status,
    String error,
    Object message
) {}
