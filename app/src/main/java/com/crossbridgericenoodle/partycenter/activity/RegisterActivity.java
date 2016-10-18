package com.crossbridgericenoodle.partycenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.crossbridgericenoodle.partycenter.Api;
import com.crossbridgericenoodle.partycenter.App;
import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.UserManager;
import com.crossbridgericenoodle.partycenter.base.BaseActivity;
import com.crossbridgericenoodle.partycenter.model.User;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText nameText;
    private EditText emailText;
    private RadioGroup sexSelect;
    private RadioGroup typeSelect;
    private EditText passwordText;
    private EditText passwordConfirmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameText = (EditText) findViewById(R.id.et_name);
        emailText = (EditText) findViewById(R.id.et_email);
        passwordText = (EditText) findViewById(R.id.et_password);
        passwordConfirmText = (EditText) findViewById(R.id.et_confirm_password);
        Button registerButton = (Button) findViewById(R.id.bt_register);
        if (registerButton != null) {
            registerButton.setOnClickListener(this);
        }
        sexSelect = (RadioGroup) findViewById(R.id.rg_sex);
        typeSelect = (RadioGroup) findViewById(R.id.rg_type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register: {
                if (isNameValid()) {
                    if (isEmailValid()) {
                        if (isPasswordValid()) {
                            if (isPasswordInputEqual()) {
                                final int sex, type;
                                if (sexSelect.getCheckedRadioButtonId() == R.id.rb_male) {
                                    sex = User.SEX_MAN;
                                } else {
                                    sex = User.SEX_WOMAN;
                                }
                                if (typeSelect.getCheckedRadioButtonId() == R.id.rb_audience) {
                                    type = User.TYPE_AUDIENCE;
                                } else {
                                    type = User.TYPE_HOST;
                                }
                                Api.getInstance().register(nameText.getText().toString(),
                                        emailText.getText().toString(),
                                        passwordText.getText().toString(),
                                        type,//TODO type和sex到底要怎么传
                                        new Api.OnResultListener<Integer>() {
                                            @Override
                                            public void getResult(Integer resultCode) {
                                                switch (resultCode) {
                                                    case Api.REGISTER_OK: {
                                                        Api.getInstance().getUserInfo(new Api.OnResultListener<User>() {
                                                            @Override
                                                            public void getResult(User user) {
                                                                UserManager.login(user);
                                                            }
                                                        });
                                                        Toast.makeText(App.getContext(),
                                                                "注册成功", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(App.getContext(), MainActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    }
                                                    case Api.REGISTER_EMAIL_CONFLICT: {
                                                        Toast.makeText(App.getContext(),
                                                                "邮箱已被使用", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                    case Api.REGISTER_USERNAME_CONFLICT: {
                                                        Toast.makeText(App.getContext(),
                                                                "用户名已被使用", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                    case Api.REGISTER_SYS_ERR: {
                                                        Toast.makeText(App.getContext(),
                                                                "系统错误", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                }
                                            }
                                        });
                            } else {
                                passwordConfirmText.setError("密码不对应");
                            }
                        } else {
                            passwordText.setError("密码太短");
                        }
                    } else {
                        emailText.setError("邮箱格式错误");
                    }
                } else {
                    nameText.setError("名字太短");
                }
                break;
            }
            default:
                break;
        }
    }

    private boolean isPasswordValid() {
        return passwordText.getText().toString().length() >= 6;
    }

    private boolean isPasswordInputEqual() {
        return passwordText.getText().toString().equals(passwordConfirmText.getText().toString());
    }

    private boolean isEmailValid() {
        return emailText.getText().toString().contains("@");
    }

    private boolean isNameValid() {
        return nameText.getText().toString().length() >= 4;
    }

}
