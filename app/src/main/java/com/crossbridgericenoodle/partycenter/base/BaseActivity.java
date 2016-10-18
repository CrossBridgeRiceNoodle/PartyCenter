package com.crossbridgericenoodle.partycenter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crossbridgericenoodle.partycenter.UserManager;

public class BaseActivity extends AppCompatActivity {
    private static final int MENU_ITEM_LOGIN = 0;
    private static final int MENU_ITEM_LOGOUT = 1;
    private static final int MENU_ITEM_EXIT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 每次使用菜单，对其进行动态加载
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        if (UserManager.isLogined()) {
            menu.add(0, MENU_ITEM_LOGIN, 1, "退出登录");
        } else {
            menu.add(0, MENU_ITEM_LOGOUT, 1, "登录");
        }
        menu.add(0, MENU_ITEM_EXIT, 2, "退出系统");
        return true;
    }

    /**
     * 当点击了菜单栏中的某一个子项（按钮）后调用此方法，相当于是一个按钮的监听器
     *
     * @param item 这个item是确定了点击的那个item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_LOGIN: {
                ActivityCollector.stepToLogin(this);
                break;
            }
            case MENU_ITEM_LOGOUT: {
                ActivityCollector.loginAgain(this);
                break;
            }
            case MENU_ITEM_EXIT: {
                ActivityCollector.finishAll();
                break;
            }
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
