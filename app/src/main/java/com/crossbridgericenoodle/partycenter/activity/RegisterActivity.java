package com.crossbridgericenoodle.partycenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.crossbridgericenoodle.partycenter.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText email;
    private RadioGroup sex;
    private RadioGroup type;
    private EditText password;
    private EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        passwordConfirm = (EditText) findViewById(R.id.et_confirm_password);

    }
}
