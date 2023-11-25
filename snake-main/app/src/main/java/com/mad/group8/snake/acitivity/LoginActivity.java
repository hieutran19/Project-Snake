package com.mad.group8.snake.acitivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mad.group8.snake.R;
import com.mad.group8.snake.dbhelper.UserDatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    UserDatabaseHelper db;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new UserDatabaseHelper(this);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });
    }

    public void onLoginClick(View view){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        Cursor user = db.getUser(username, password);

        if(user.getCount() > 0){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}