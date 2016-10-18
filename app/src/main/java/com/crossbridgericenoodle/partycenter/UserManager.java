package com.crossbridgericenoodle.partycenter;

import com.crossbridgericenoodle.partycenter.model.User;

/**
 * Created by 92019 on 2016/10/17.
 * 记录用户登录状态的全局类
 */

public class UserManager {
    public static User currentUser = null;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLogined() {
        return currentUser != null;
    }
}
