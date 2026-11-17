package com.expensetracker.expense_tracker_api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.expense_tracker_api.dto.ExpenseRequest;
import com.expensetracker.expense_tracker_api.dto.ExpenseResponse;
import com.expensetracker.expense_tracker_api.dto.ExpenseSummaryResponse;
import com.expensetracker.expense_tracker_api.model.Category;
import com.expensetracker.expense_tracker_api.service.ExpenseService;

import jakarta.validation.Valid;

/**
 * REST Controller exposing CRUD operations and monthly analytics for expenses.
 */
@RestController 
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    /**
     * Constructor injection: Spring supplies the ExpenseService bean automatically.
     */
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * POST /api/v1/expenses - Creates a new expense (HTTP 201 Created).
     */
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/v1/expenses - Retrieves expenses with optional category and date filtering.
     */
    @GetMapping 
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(
        @RequestParam(required=false) Category category,
        @RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<ExpenseResponse> expenses = expenseService.getAllExpenses(category, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    /**
     * GET /api/v1/expenses/{id} - Fetches an expense by its unique identifier.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        ExpenseResponse response = expenseService.getExpenseById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/v1/expenses/{id} - Updates an existing expense record.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
        @PathVariable Long id,
        @Valid @RequestBody ExpenseRequest request
    )  {
        ExpenseResponse response = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/v1/expenses/{id} - Deletes an expense (HTTP 204 No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/v1/expenses/summary - Computes monthly spending total and category breakdown.
     */
    @GetMapping("/summary")
    public ResponseEntity<ExpenseSummaryResponse> getMonthlySummary(
        @RequestParam int year,
        @RequestParam int month
    ) {
        ExpenseSummaryResponse summary = expenseService.getMonthlySummary(year, month);
        return ResponseEntity.ok(summary);
    }

}
