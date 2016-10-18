package com.crossbridgericenoodle.partycenter.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.crossbridgericenoodle.partycenter.UserManager;
import com.crossbridgericenoodle.partycenter.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void loginAgain(Context context) {
        UserManager.logout();
        stepToLogin(context);
    }

    public static void stepToLogin(Context context) {
        finishAll();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("LOGIN_PURPOSE", true);
        context.startActivity(intent);
    }
}
