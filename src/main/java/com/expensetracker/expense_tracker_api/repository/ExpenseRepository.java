package com.expensetracker.expense_tracker_api.repository;

import com.expensetracker.expense_tracker_api.model.Category;
import com.expensetracker.expense_tracker_api.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Layer for Expense entities.
 * Spring Data JPA automatically generates SQL queries based on method names.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Finds all expenses matching a specific category (e.g. FOOD, TRANSPORT).
     */
    List<Expense> findByCategory(Category category);

    /**
     * Finds all expenses logged between two dates (inclusive).
     */
    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Finds all expenses matching both a specific category AND date range.
     */
    List<Expense> findByCategoryAndExpenseDateBetween(Category category, LocalDate startDate, LocalDate endDate);
}
