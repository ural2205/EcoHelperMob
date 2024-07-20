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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTeam extends Fragment {
    private ListView teamListView;
    private ModelTeam[] teamsArray;
    private TeamAdapter adapter;
    SharedPreferences mSettings;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_team, container, false);
        teamListView = (ListView) view.findViewById(R.id.teamListView);
        Button create = view.findViewById(R.id.createTeam);
        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        int userId = 0;
        int userIdAuth = 0;

        if(mSettings.contains(APP_PREFERENCES_USERID)) {
            userId = mSettings.getInt(APP_PREFERENCES_USERID, userId);
            userIdAuth = mSettings.getInt(APP_PREFERENCES_USERID,userIdAuth);
        }
        int finalUserId = userId;
        int finalUserIdAuth = userIdAuth;
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getLevel(finalUserId, finalUserIdAuth, view);

            }
        });
        Button backToTask = view.findViewById(R.id.backToTask);
        backToTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainPage.class);
                getActivity().startActivity(intent);
            }
        });
        getTeams(view);
        return view;
    }



    public void getLevel(int userId, int userIdAuth, View view){
        if (userIdAuth != 0) {
            userId = userIdAuth;
        }
        Log.d("create", "onClick: " + userId);
        retrofit2.Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserLevel(userId);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    int level = Integer.parseInt(response.body().string());
                    Log.d("create", "onResponse: " + level);
                    if(level >= 5){
                        FragmentCreateTeam f1 = new FragmentCreateTeam();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.conteinerTeam, f1)
                                .commit();
                    }
                    else{
                        Toast.makeText(getActivity(), "Команду можно создавать с 5 уровня", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "емае", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTeams(View view){
        Call<List<ModelTeam>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getTeams();
        call.enqueue(new Callback<List<ModelTeam>>() {
            @Override
            public void onResponse(Call<List<ModelTeam>> call, Response<List<ModelTeam>> response) {
                List<ModelTeam> teamBody = response.body();
                teamsArray = teamBody.toArray(new ModelTeam[0]);

                teamListView = (ListView) view.findViewById(R.id.teamListView);
                adapter = new TeamAdapter(getContext(), teamsArray,
                        modelTeam -> {
                            FragmentTeamDescription fragment = new FragmentTeamDescription();
                            Bundle bundle = new Bundle();
                            Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()
                                    .create();
                            String teamJson = gson.toJson(modelTeam);
                            bundle.putString("team", teamJson);
                            fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.conteinerTeam, fragment, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }

                        );
                teamListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ModelTeam>> call, Throwable t) {
                Log.d("TEAM", "FAIL: " + t);
            }
        });
    }
}