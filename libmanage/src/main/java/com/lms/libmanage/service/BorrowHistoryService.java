package com.lms.libmanage.service;

import com.lms.libmanage.entity.Book;
import com.lms.libmanage.entity.BorrowHistory;
import com.lms.libmanage.exception.NotFoundException;
import com.lms.libmanage.repository.BookRepository;
import com.lms.libmanage.repository.BorrowHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowHistoryService {

    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    @Autowired
    private BookRepository bookRepository;

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

    public BorrowHistory borrowBook(Integer bookId, Integer userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + bookId));

        if (book.getNoOfCopies() <= 0) {
            throw new IllegalStateException("Book is out of stock");
        }

        book.setNoOfCopies(book.getNoOfCopies() - 1);
        bookRepository.save(book);

        BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setBookId(bookId);
        borrowHistory.setUserId(userId);
        borrowHistory.setIssueDate(new Date());
        borrowHistory.setDueDate(new Date(System.currentTimeMillis() + (14L * 24 * 60 * 60 * 1000))); // 2 sedmice

        return borrowHistoryRepository.save(borrowHistory);
    }
}
