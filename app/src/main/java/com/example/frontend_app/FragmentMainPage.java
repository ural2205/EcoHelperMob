package com.example.frontend_app;

import android.content.Intent;
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

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMainPage extends Fragment {
    private ListView taskListView;
    private Task[] tasksArray;
    private TaskAdapter adapter;

    Button back, team, play, userProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page,
                container, false);
        taskListView = (ListView) view.findViewById(R.id.taskListView);
        team = view.findViewById(R.id.team);
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Team.class);
                getActivity().startActivity(intent);
            }

        });


        userProfile = view.findViewById(R.id.userProfile);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserProfile.class);
                getActivity().startActivity(intent);
            }
        });
        getAllTasks(view);
        return view;
    }
    private void getAllTasks(View view){

        Call<List<Task>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task> tasksBody = response.body();
                tasksArray = tasksBody.toArray(new Task[0]);
                Log.d("taskBody", tasksBody.toString());
//                Toast.makeText(this, "HAHAHHA", Toast.LENGTH_SHORT).show();
                taskListView = (ListView) view.findViewById(R.id.taskListView);
                adapter = new TaskAdapter(getContext(), tasksArray,
                        task -> {
                            Log.d("MAIN_PAGE_FRAGMENT_RESPONSE_HANDLER", "accept: " + task);
                            FragmentDescriptionTask fragment = new FragmentDescriptionTask();
                            Bundle bundle = new Bundle();
                            Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()
                                    .create();
                            String taskJson = gson.toJson(task);
                            bundle.putString("task", taskJson);
                            fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.conteiner, fragment, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        });
                taskListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.d("TASK", "FAIL: " + t);
            }
        });
    }


}