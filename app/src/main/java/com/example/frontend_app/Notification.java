package com.example.frontend_app;

import static com.example.frontend_app.FragmentResponseTask.APP_PREFERENCES_TASK;
import static com.example.frontend_app.FragmentResponseTask.APP_PREFERENCES_TASKID;
import static com.example.frontend_app.Register.APP_PREFERENCES_NAME;
import static com.example.frontend_app.Register.APP_PREFERENCES_USERID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {

    SharedPreferences mSettings;
    SharedPreferences mSettings1;
    TextView nameTask, descriptionTask, process;
    Button begin, backToUserProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        nameTask = findViewById(R.id.nameTask);
        descriptionTask = findViewById(R.id.desriptionTaskk);
        process = findViewById(R.id.process);
        begin = findViewById(R.id.begin);
        backToUserProfile = findViewById(R.id.backToUserProfile);
        backToUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notification.this, UserProfile.class);
                startActivity(intent);
            }
        });
        mSettings = getSharedPreferences(APP_PREFERENCES_TASK, Context.MODE_PRIVATE);
        mSettings1 = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        int userId = 0;
        int userIdAuth = 0;
        int taskId = 0;

        if(mSettings.contains(APP_PREFERENCES_TASKID)) {
            taskId = mSettings.getInt(APP_PREFERENCES_TASKID, taskId);
        }
        if(mSettings.contains(APP_PREFERENCES_USERID)) {
            userId = mSettings.getInt(APP_PREFERENCES_USERID, userId);
            userIdAuth = mSettings.getInt(APP_PREFERENCES_USERID,userIdAuth);
        }
        getCheckTask(taskId, userId, userIdAuth);
    }
    public void getCheckTask(int taskId, int userId, int userIdAuth){
        if (userIdAuth != 0) {
            userId = userIdAuth;
        }

        Call<Task> call = RetrofitClient
                .getInstance()
                .getApi()
                .checkStatus(taskId);
        int finalUserId = userId;
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                Task task = response.body();
                nameTask.setText(task.name);
                descriptionTask.setText(task.description);
                if(task.getStatus() == 1){
                    process.setText("ЗАЯВКА РАССМАТРИВАЕТСЯ");
                }
                else if(task.getStatus() == 2){
                    process.setText("ЗАЯВКА ОДОБРЕНА");

                    begin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            changeStatus(taskId);

                        }
                    });
                }
                else if (task.getStatus() == 3){
                    process.setText("Давай делай!");
                    begin.setText("Сдать");
                    begin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkReadyTask(taskId);
                            process.setText("РАБОТА НА МОДЕРАЦИИ");
                        }
                    });
                }
                else if(task.getStatus() == 4){
                    process.setText("Получи опыт");
                    begin.setText("Получить опыт");
                    begin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getXp(finalUserId, taskId);
                        }
                    });
                }
                else{
                    process.setText("ЗАЯВКА ОТКЛОНЕНА");
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                Toast.makeText(Notification.this, "", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void changeStatus(int taskId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .change_status(taskId, 3);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Notification.this, "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Notification.this, Notification.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Notification.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkReadyTask(int taskId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .check_ready_task(taskId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Notification.this, "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Notification.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getXp(int userId, int taskId){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .get_xp(userId, taskId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Notification.this, "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Notification.this, UserProfile.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Notification.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}