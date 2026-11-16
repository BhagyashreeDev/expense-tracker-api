package com.expensetracker.expense_tracker_api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.expensetracker.expense_tracker_api.dto.ExpenseRequest;
import com.expensetracker.expense_tracker_api.dto.ExpenseResponse;
import com.expensetracker.expense_tracker_api.dto.ExpenseSummaryResponse;
import com.expensetracker.expense_tracker_api.exception.ResourceNotFoundException;
import com.expensetracker.expense_tracker_api.model.Category;
import com.expensetracker.expense_tracker_api.model.Expense;
import com.expensetracker.expense_tracker_api.repository.ExpenseRepository;

@Service 
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ExpenseResponse createExpense(ExpenseRequest request) {
        Expense expense = new Expense(
            request.title(),
            request.amount(),
            request.category(),
            request.expenseDate()
        );
        Expense saved = expenseRepository.save(expense);
        return ExpenseResponse.fromEntity(expense);
    }

    public List<ExpenseResponse> getAllExpenses(Category category, LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses;

        if(category!=null && startDate!=null && endDate !=null) {
            expenses = expenseRepository.findByCategoryAndExpenseDateBetween(category, startDate, endDate);
        } else if(category!=null) {
            expenses = expenseRepository.findByCategory(category);
        } else if(startDate != null && endDate !=null) {
            expenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);
        } else {
            expenses = expenseRepository.findAll();
        }

        return expenses.stream().map(ExpenseResponse::fromEntity).toList();
    }

    public ExpenseResponse getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: "+id));
        return ExpenseResponse.fromEntity(expense);
    }

    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: "+id));

        expense.setTitle(request.title());
        expense.setAmount(request.amount());
        expense.setCategory(request.category());
        expense.setExpenseDate(request.expenseDate());

        Expense updated = expenseRepository.save(expense);
        return ExpenseResponse.fromEntity(updated);
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with id: "+id);
        }
        expenseRepository.deleteById(id);
    }

    public ExpenseSummaryResponse getMonthlySummary(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Expense> monthlyExpenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);
        BigDecimal totalSpent = BigDecimal.ZERO;
        Map<Category, BigDecimal> categoryMap = new EnumMap<>(Category.class);

        for (Expense e : monthlyExpenses) {
            totalSpent = totalSpent.add(e.getAmount());
            categoryMap.merge(e.getCategory(), e.getAmount(), BigDecimal::add);
        }

        return new ExpenseSummaryResponse(totalSpent, monthlyExpenses.size(), categoryMap);
    }
}
