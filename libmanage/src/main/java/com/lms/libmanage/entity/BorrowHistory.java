package com.lms.libmanage.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "borrow_history")
public class BorrowHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id", nullable = false, updatable = false)
    private Integer borrowId;

    @NotNull(message = "Book ID cannot be null")
    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @NotNull(message = "User ID cannot be null")
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull(message = "Issue date cannot be null")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDataSerializer.class)
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDataSerializer.class)
    @Column(name = "return_date")
    private Date returnDate;

    @NotNull(message = "Due date cannot be null")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDataSerializer.class)

    @Column(name = "due_date", nullable = false)
    private Date dueDate;
}
