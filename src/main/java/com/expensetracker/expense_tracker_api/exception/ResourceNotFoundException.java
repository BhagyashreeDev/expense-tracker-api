package com.expensetracker.expense_tracker_api.exception;

/**
 * Custom runtime exception thrown when a requested resource (e.g. Expense ID)
 * does not exist in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
