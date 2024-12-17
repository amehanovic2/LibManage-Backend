package com.lms.libmanage.service;

import com.lms.libmanage.entity.BorrowHistory;
import com.lms.libmanage.exception.NotFoundException;
import com.lms.libmanage.repository.BorrowHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowHistoryService {

    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    public List<BorrowHistory> getAllBorrowHistory() {
        return borrowHistoryRepository.findAll();
    }

    public BorrowHistory getBorrowHistoryById(Integer id) {
        return borrowHistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Borrow history not found with ID: " + id));
    }

    public BorrowHistory createBorrowHistory(BorrowHistory borrowHistory) {
        return borrowHistoryRepository.save(borrowHistory);
    }

    public BorrowHistory updateBorrowHistory(Integer id, BorrowHistory borrowHistoryDetails) {
        BorrowHistory borrowHistory = getBorrowHistoryById(id);
        borrowHistory.setBookId(borrowHistoryDetails.getBookId());
        borrowHistory.setUserId(borrowHistoryDetails.getUserId());
        borrowHistory.setIssueDate(borrowHistoryDetails.getIssueDate());
        borrowHistory.setReturnDate(borrowHistoryDetails.getReturnDate());
        borrowHistory.setDueDate(borrowHistoryDetails.getDueDate());
        return borrowHistoryRepository.save(borrowHistory);
    }

    public void deleteBorrowHistory(Integer id) {
        BorrowHistory borrowHistory = getBorrowHistoryById(id);
        borrowHistoryRepository.delete(borrowHistory);
    }
}
