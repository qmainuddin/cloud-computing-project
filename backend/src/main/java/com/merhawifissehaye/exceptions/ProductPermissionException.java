package com.merhawifissehaye.exceptions;

public class ProductPermissionException extends Exception {
    @Override
    public String getMessage() {
        return "You are not the owner of this product";
    }
}
