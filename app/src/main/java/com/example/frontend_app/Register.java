package com.example.frontend_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

public class Register extends AppCompatActivity {

    private static final String TAG = "Register";
    Button register, authorization;
    EditText editTextUsername;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextEmail;
    EditText editTextPassword;

    Button button;
    public static final String APP_PREFERENCES_NAME = "MY_PREFERENCES";
    public static final String APP_PREFERENCES_USERID = "userId";
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        editTextUsername = findViewById(R.id.username);
        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.surname);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        Button register = findViewById(R.id.button3);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void userSignUp() {
        String username = editTextUsername.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        retrofit2.Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(username, name, surname, email, password);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    int userId = Integer.parseInt(response.body().string());
                    if(userId != 0){
                        SharedPreferences.Editor editor = mSettings.edit();
                        Log.d("TAG", "onResponse: " + response.body().string());

                        editor.putInt(APP_PREFERENCES_USERID, userId);
                        editor.apply();

                        Intent intent1 = new Intent(Register.this, MainPage.class);
                        startActivity(intent1);
                        Toast.makeText(Register.this, "" + userId, Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(Register.this, "email is incorrect", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signIn(View v) {

        try {
            Intent intent = new Intent(Register.this, Authorization.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v){
        userSignUp();
    }
}

















