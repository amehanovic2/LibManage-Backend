package com.lms.libmanage.controller;

import com.lms.libmanage.entity.BorrowHistory;
import com.lms.libmanage.service.BorrowHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow-history")
public class BorrowHistoryController {

    @Autowired
    private BorrowHistoryService borrowHistoryService;

    @GetMapping
    public ResponseEntity<List<BorrowHistory>> getAllBorrowHistory() {
        List<BorrowHistory> borrowHistories = borrowHistoryService.getAllBorrowHistory();
        return ResponseEntity.ok(borrowHistories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowHistory> getBorrowHistoryById(@PathVariable Integer id) {
        BorrowHistory borrowHistory = borrowHistoryService.getBorrowHistoryById(id);
        return ResponseEntity.ok(borrowHistory);
    }

    @PostMapping
    public ResponseEntity<BorrowHistory> createBorrowHistory(@Valid @RequestBody BorrowHistory borrowHistory) {
        BorrowHistory newBorrowHistory = borrowHistoryService.createBorrowHistory(borrowHistory);
        return new ResponseEntity<>(newBorrowHistory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowHistory> updateBorrowHistory(@PathVariable Integer id, @Valid @RequestBody BorrowHistory borrowHistoryDetails) {
        BorrowHistory updatedBorrowHistory = borrowHistoryService.updateBorrowHistory(id, borrowHistoryDetails);
        return ResponseEntity.ok(updatedBorrowHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowHistory(@PathVariable Integer id) {
        borrowHistoryService.deleteBorrowHistory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
