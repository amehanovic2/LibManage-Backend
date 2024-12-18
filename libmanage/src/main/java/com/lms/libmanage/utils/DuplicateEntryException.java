package com.lms.libmanage.utils;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String errorMessage) {
        super(errorMessage);
    }
}