package com.example.frontend_app;

import static com.example.frontend_app.Register.APP_PREFERENCES_NAME;
import static com.example.frontend_app.Register.APP_PREFERENCES_USERID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class FragmentResponseTask extends Fragment {


    EditText editTextOffer;

    Button send;
    SharedPreferences mSettings;

    public static final String APP_PREFERENCES_TASK = "MY_PREFERENCES";
    public static final String APP_PREFERENCES_TASKID = "taskId";
    SharedPreferences mSettings1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText editTextOffer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_response_task, container, false);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES_TASK, Context.MODE_PRIVATE);
        int userId = 0;
        int userIdAuth = 0;

        if(mSettings.contains(APP_PREFERENCES_USERID)) {
            userId = mSettings.getInt(APP_PREFERENCES_USERID, userId);
            userIdAuth = mSettings.getInt(APP_PREFERENCES_USERID,userIdAuth);
        }

        editTextOffer =(EditText) view.findViewById(R.id.offer);

        Button send = view.findViewById(R.id.sendOffer);
        int finalUserIdAuth = userIdAuth;
        int finalUserId = userId;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResponseTask(view, finalUserId, finalUserIdAuth);
            }
        });

        Button backMainPage = view.findViewById(R.id.backMainPage);
        backMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainPage.class);
                getActivity().startActivity(intent);
            }
        });


        return view;
    }
    public void ResponseTask(View view, int userId, int userIdAuth){
        if (userIdAuth != 0) {
            userId = userIdAuth;
        }
        String offer = editTextOffer.getText().toString();

        Bundle bundle = this.getArguments();
        Task task = new Gson().fromJson(bundle.getString("task"), Task.class);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_TASKID, task.id);
        editor.apply();
        retrofit2.Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .responseTask(offer, task.id, userId);

        call.enqueue(new retrofit2.Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = response.body().toString();
                Toast.makeText(getActivity(), "Отправлено", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainPage.class);
                startActivity(intent);
            }



            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}