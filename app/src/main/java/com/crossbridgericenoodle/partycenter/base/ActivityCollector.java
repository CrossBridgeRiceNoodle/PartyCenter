package com.crossbridgericenoodle.partycenter.base;

import android.app.Activity;
import android.content.Context;

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

    public static void signInAgain(Context context) {
//        finishAll();
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
    }
}
