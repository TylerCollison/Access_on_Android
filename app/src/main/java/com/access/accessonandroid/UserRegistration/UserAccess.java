package com.access.accessonandroid.UserRegistration;

/**
 *
 */
public interface UserAccess {

    Boolean isUser(String username);

    String getUserPassword();

    void updateUserPassword(String password);
}
