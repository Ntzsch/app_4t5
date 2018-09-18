package edu.gatech.cs2340.app_4t5.models;

import java.util.HashMap;

public class UserRecords {
    private static HashMap<String, User> users = new HashMap<>();

    /**
     * @param user user to add
     * @return true if user was added successfully, false if otherwise
     */
    public static boolean add_user(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }

    /**
     * @param user user to remove
     * @return true if user was removed successfully, false if otherwise
     */
    public static boolean remove_user(User user) {
        if (!users.containsKey(user.getUsername())) {
            return false;
        }
        users.remove(user);
        return true;
    }

    /**
     * @param user
     * @return returns true if it contains the user, otherwise false
     */
    public static boolean contains_user(User user) {
        return users.containsKey(user.getUsername());
    }

    /**
     *
     * @param username user to get
     * @return the user that was gotten
     */
    public static User get_user(String username) {
        return users.get(username);
    }
}
