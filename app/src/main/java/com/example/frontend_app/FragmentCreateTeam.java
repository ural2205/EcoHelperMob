package com.example.frontend_app;

import static com.example.frontend_app.Register.APP_PREFERENCES_NAME;
import static com.example.frontend_app.Register.APP_PREFERENCES_USERID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class FragmentCreateTeam extends Fragment {

    EditText editTextNameTeam, editTextDescriptionTeam;
    SharedPreferences mSettings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_team, container, false);
        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        int userId = 0;
        int userIdAuth = 0;

        if(mSettings.contains(APP_PREFERENCES_USERID)) {
            userId = mSettings.getInt(APP_PREFERENCES_USERID, userId);
            userIdAuth = mSettings.getInt(APP_PREFERENCES_USERID,userIdAuth);
        }
        int finalUserId = userId;
        int finalUserIdAuth = userIdAuth;
        editTextDescriptionTeam = view.findViewById(R.id.DescriptionNewTeam);
        editTextNameTeam = view.findViewById(R.id.nameNewTeam);
        Button back = view.findViewById(R.id.backToTeams);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Team.class);
                getActivity().startActivity(intent);
            }
        });

        Button createTeam = view.findViewById(R.id.createTeamNow);
        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTeam(finalUserId, finalUserIdAuth);
            }
        });

        return view;

    }

    public void createTeam(int userId, int userIdAuth){
        if (userIdAuth != 0) {
            userId = userIdAuth;
        }
        String nameTeam = editTextNameTeam.getText().toString().trim();
        String descriptionTeam = editTextDescriptionTeam.getText().toString().trim();

        retrofit2.Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createTeam(nameTeam, descriptionTeam, userId);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = response.body().toString();
                Intent intent = new Intent();
                intent.setClass(getActivity(), Team.class);
                getActivity().startActivity(intent);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "bad", Toast.LENGTH_SHORT).show();
            }
        });
    }
}