package com.zy.graduation1.common;

import lombok.Data;

public class RequestUser {

    public static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void put(User user) {
        currentUser.set(user);
    }

    public static Long getCurrentUser() {
        User user = currentUser.get();
        return user != null ? user.getUserId() : null;
    }

    public static void clear() {
        currentUser.remove();
    }

    @Data
    public static class User {
        private Long userId;
    }
}
