package org.example.workforce.controller;

import org.example.workforce.dto.ExpenseActionRequest;
import org.example.workforce.model.Expense;
import org.example.workforce.model.enums.ExpenseStatus;
import org.example.workforce.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/expenses")
public class ManagerExpenseController {

    @Autowired private ExpenseService expenseService;
    @GetMapping
    public ResponseEntity<Page<Expense>> getTeamExpenses(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(expenseService.getTeamExpenses(auth.getName(),
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "submittedDate"))));
    }

    @GetMapping("/team-all")
    public ResponseEntity<Page<Expense>> getAllTeamExpenses(
            Authentication auth,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ExpenseStatus expenseStatus = null;
        if (status != null) {
            try { expenseStatus = ExpenseStatus.valueOf(status.toUpperCase()); } catch (Exception ignored) {}
        }
        return ResponseEntity.ok(expenseService.getAllTeamExpenses(auth.getName(), expenseStatus,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))));
    }

    @GetMapping("/team-finance-pending")
    public ResponseEntity<Page<Expense>> getTeamFinancePending(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(expenseService.getTeamFinancePendingExpenses(auth.getName(),
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "managerActionDate"))));
    }
    @PatchMapping("/{id}/action")
    public ResponseEntity<Expense> actionExpense(
            Authentication auth,
            @PathVariable Integer id,
            @RequestBody ExpenseActionRequest request) {
        return ResponseEntity.ok(expenseService.managerAction(auth.getName(), id, request));
    }

    @GetMapping("/{id}/receipt")
    public ResponseEntity<Resource> getExpenseReceipt(Authentication auth, @PathVariable Integer id) {
        ExpenseService.ReceiptFileData receipt = expenseService.getExpenseReceipt(auth.getName(), id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + receipt.fileName() + "\"")
                .contentType(MediaType.parseMediaType(receipt.contentType()))
                .body(receipt.resource());
    }
    @GetMapping("/all")
    public ResponseEntity<Page<Expense>> getAllExpenses(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ExpenseStatus expenseStatus = null;
        if (status != null) {
            try { expenseStatus = ExpenseStatus.valueOf(status.toUpperCase()); } catch (Exception ignored) {}
        }
        return ResponseEntity.ok(expenseService.getAllExpenses(expenseStatus,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))));
    }
    @GetMapping("/finance-pending")
    public ResponseEntity<Page<Expense>> getFinancePending(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(expenseService.getFinancePendingExpenses(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "managerActionDate"))));
    }
    @PatchMapping("/{id}/finance-action")
    public ResponseEntity<Expense> financeAction(
            Authentication auth,
            @PathVariable Integer id,
            @RequestBody ExpenseActionRequest request) {
        return ResponseEntity.ok(expenseService.financeAction(auth.getName(), id, request));
    }
}

