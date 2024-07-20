package com.example.frontend_app;

import static com.example.frontend_app.Authorization.APP_PREFERENCES_NAME_AUTH;
import static com.example.frontend_app.Authorization.APP_PREFERENCES_USERID_AUTH;
import static com.example.frontend_app.Register.APP_PREFERENCES_NAME;
import static com.example.frontend_app.Register.APP_PREFERENCES_USERID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {
    private static final String TAG = "UserProfile";

    TextView usernameUser, nameUser, surnameUser, levelUser, xpUser, emailUser;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Button backToTasks = findViewById(R.id.backToTasksss);
        backToTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, MainPage.class);
                startActivity(intent);
            }
        });
        mSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        int userId = 0;
        int userIdAuth = 0;

        Button notifications = findViewById(R.id.notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, Notification.class);
                startActivity(intent);

            }
        });

        if(mSettings.contains(APP_PREFERENCES_USERID)) {
            userId = mSettings.getInt(APP_PREFERENCES_USERID, userId);
            userIdAuth = mSettings.getInt(APP_PREFERENCES_USERID,userIdAuth);
        }
        Log.d(TAG, "onCreate: " + userIdAuth);
        getUserProfile(userId, userIdAuth);
    }

    public void getUserProfile(int userId, int userIdAuth){
        if (userIdAuth != 0) {
            userId = userIdAuth;
        }

        Log.d(TAG, "getUserProfile: userId: " + userId);
        retrofit2.Call<ModelUserProfile> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserProfile(userId);

        call.enqueue(new retrofit2.Callback<ModelUserProfile>(){

            @Override
            public void onResponse(Call<ModelUserProfile> call, Response<ModelUserProfile> response) {
                ModelUserProfile userProfile = response.body();
                nameUser = (TextView) findViewById(R.id.nameUser);
                usernameUser = (TextView) findViewById(R.id.usernameUser);
                surnameUser = (TextView) findViewById(R.id.surnameUser);
                emailUser = (TextView) findViewById(R.id.emailUser);
                levelUser = (TextView) findViewById(R.id.levelUser);
                xpUser = (TextView) findViewById(R.id.xpUser);
                usernameUser.setText("НИКНЕЙМ: " + userProfile.username);
                nameUser.setText("ИМЯ:" + userProfile.name);
                surnameUser.setText("ФАМИЛИЯ: " + userProfile.surname);
                emailUser.setText("ПОЧТА: " + userProfile.email);
                levelUser.setText("УРОВЕНЬ: " + String.valueOf(userProfile.level));
                xpUser.setText("ОПЫТ: " + String.valueOf(userProfile.xp));
            }

            @Override
            public void onFailure(Call<ModelUserProfile> call, Throwable t) {
                Toast.makeText(UserProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}