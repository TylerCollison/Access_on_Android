package com.access.accessonandroid.UserRegistration;

/**
 * @author Megan Goins
 *
 * This defines the interface for a user-access entity, which is used to verify and manipulate
 * user credential data.
 */
public interface UserAccess {

    /**
     * Determines whether the username is valid
     * @param username The username to be checked
     * @return Whether the username was valid
     */
    Boolean isUser(String username);

    /**
     * Gets the user's password
     * @return The currently logged-in user's password
     */
    String getUserPassword();

    /**
     * Updates the user's password according to the specified string
     * @param password The new password
     */
    void updateUserPassword(String password);
}
