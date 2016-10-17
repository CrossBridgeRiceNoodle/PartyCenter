package com.crossbridgericenoodle.partycenter.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crossbridgericenoodle.partycenter.R;

public class BaseActivity extends AppCompatActivity {

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
     * 当点击右上角菜单键的时候调用此方法
     *
     * @param menu 此处为展示这个菜单，菜单布局为R.menu.menu_main
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_exit_login: {
                ActivityCollector.signInAgain(this);
                break;
            }
            case R.id.action_exit_system: {
                ActivityCollector.finishAll();
                break;
            }
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
