package com.mad.group8.snake.acitivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mad.group8.snake.R;
import com.mad.group8.snake.dbhelper.UserDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etPassword , etConfirmPassword;
    UserDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new UserDatabaseHelper(this);
        etConfirmPassword = findViewById(R.id.et_password_confirm);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
    }

    public void onRegisterClick(View view){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirm = etConfirmPassword.getText().toString();

        if (!password.equals(passwordConfirm)){
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            boolean isInserted = db.insertUser(username, password);
            if(isInserted) {
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
