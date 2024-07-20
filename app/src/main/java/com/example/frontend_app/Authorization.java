package com.example.frontend_app;

import static com.example.frontend_app.Register.APP_PREFERENCES_NAME;
import static com.example.frontend_app.Register.APP_PREFERENCES_USERID;

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

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class Authorization extends AppCompatActivity {


    EditText editTextEmail;

    EditText editTextPassword;

    Button button2;
    public static final String APP_PREFERENCES_NAME_AUTH = "MY_PREFERENCES";
    public static final String APP_PREFERENCES_USERID_AUTH = "userIdAuth";
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        button2 = findViewById(R.id.button2);
        mSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Button auth = findViewById(R.id.button4);
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void userSignIn(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        retrofit2.Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .authorization(email, password);
        Log.d("AUTHORIZATION", "user sign in calling");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    int s = Integer.parseInt(response.body().string());
                    Log.d("t", "" + s);
                    if(s == -1){
                        Toast.makeText(Authorization.this, "the password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                    else if(s == -2){
                        Toast.makeText(Authorization.this, "the email is incorrect", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putInt(APP_PREFERENCES_USERID, s);
                        editor.apply();
                        Intent intent = new Intent(Authorization.this, MainPage.class);
                        startActivity(intent);
                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Authorization.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v){
        if (v.getId() == R.id.button2) {
            userSignIn();
        }
    }

    public void signUp(View v){
        try {
            Intent intent = new Intent(Authorization.this, Register.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "ошибка", Toast.LENGTH_SHORT).show();
        }
    }
}
