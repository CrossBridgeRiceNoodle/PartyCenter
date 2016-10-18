package com.crossbridgericenoodle.partycenter.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crossbridgericenoodle.partycenter.Api;
import com.crossbridgericenoodle.partycenter.App;
import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.UserManager;
import com.crossbridgericenoodle.partycenter.model.User;

import static android.content.Context.MODE_PRIVATE;


/**
 * 若已登录显示用户的信息
 * 若未登录显示登录界面
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener {

    public static final int SHOW_LAYOUT = 1;
    public static final int LOGIN_LAYOUT = 2;

    private LinearLayout showLayout;
    private LinearLayout loginLayout;
    private int currentLayout;
    View view;

    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberAccount;

    private TextView nameText;
    private TextView emailText;
    private TextView sexText;
    private TextView typeText;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_info, container, false);

        initView();

        if (UserManager.isLogined()) {
            changeToShow();
        } else {
            changeToLogin();
        }

        SharedPreferences preferences = App.getContext().getSharedPreferences("account_remember", MODE_PRIVATE);
        rememberAccount.setChecked(preferences.getBoolean("IR", false));
        if (rememberAccount.isChecked()) {
            accountEdit.setText(preferences.getString("account", ""));
            passwordEdit.setText(preferences.getString("password", ""));
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login: {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                Api.getInstance().login(account, password, new Api.OnResultListener<Integer>() {
                    @Override
                    public void getResult(Integer resultCode) {
                        switch (resultCode) {
                            case Api.LOGIN_OK: {
                                rememberAccount();
                                Toast.makeText(App.getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                Api.getInstance().getUserInfo(new Api.OnResultListener<User>() {
                                    @Override
                                    public void getResult(User user) {
                                        UserManager.login(user);
                                    }
                                });
                                changeToShow();
                                updateUserInfo();
                                break;
                            }
                            case Api.LOGIN_PASSRORD_WRONG: {
                                Toast.makeText(App.getContext(), "密码错误", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case Api.LOGIN_USERNAME_NOEXIST: {
                                Toast.makeText(App.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                });
            }
            case R.id.bt_logout: {
                Api.getInstance().logout();
                UserManager.logout();
                Toast.makeText(App.getContext(), "退出登录", Toast.LENGTH_SHORT).show();
                changeToLogin();
                break;
            }
            default:
                break;
        }
    }

    private void rememberAccount() {
        if (rememberAccount.isChecked()) {
            SharedPreferences.Editor editor = App.getContext()
                    .getSharedPreferences("account_remember", MODE_PRIVATE).edit();
            editor.putString("account", accountEdit.getText().toString());
            editor.putString("password", passwordEdit.getText().toString());
            editor.putBoolean("IR", true);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = App.getContext()
                    .getSharedPreferences("account_remember", MODE_PRIVATE).edit();
            editor.putString("account", "");
            editor.putString("password", "");
            editor.putBoolean("IR", false);
            editor.apply();
        }
    }

    private void updateUserInfo() {
        nameText.setText(UserManager.currentUser.name);
        emailText.setText(UserManager.currentUser.email);
        sexText.setText(UserManager.currentUser.sex);
        typeText.setText(UserManager.currentUser.type);
    }

    private void initView() {
        showLayout = (LinearLayout) view.findViewById(R.id.show_layout);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        accountEdit = (EditText) view.findViewById(R.id.et_account);
        passwordEdit = (EditText) view.findViewById(R.id.et_password);
        rememberAccount = (CheckBox) view.findViewById(R.id.cb_remember);
        Button loginButton = (Button) view.findViewById(R.id.bt_login);
        loginButton.setOnClickListener(this);

        nameText = (TextView) view.findViewById(R.id.tv_name);
        sexText = (TextView) view.findViewById(R.id.tv_sex);
        emailText = (TextView) view.findViewById(R.id.tv_email);
        typeText = (TextView) view.findViewById(R.id.tv_type);
        Button logoutButton = (Button) view.findViewById(R.id.bt_logout);
        logoutButton.setOnClickListener(this);
    }

    private void changeToLogin() {
        this.currentLayout = LOGIN_LAYOUT;
        showLayout.setVisibility(View.INVISIBLE);
        loginLayout.setVisibility(View.VISIBLE);
    }

    private void changeToShow() {
        this.currentLayout = SHOW_LAYOUT;
        showLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.INVISIBLE);
    }
}
