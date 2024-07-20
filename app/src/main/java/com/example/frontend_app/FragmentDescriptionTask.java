package com.example.frontend_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentDescriptionTask extends Fragment {


    private ListView taskListView;
    private TaskAdapter adapter;
    private Task task;
    TextView taskName, levelCount, descriptionTask, location;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description_task, container, false);
        getTask(view);
        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainPage.class);
                getActivity().startActivity(intent);
            }
        });

        Button response = view.findViewById(R.id.response);
        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentResponseTask f1 = new FragmentResponseTask();
                Bundle bundle = new Bundle();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                String taskJson = gson.toJson(task);
                bundle.putString("task", taskJson);
                f1.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.conteiner, f1)
                        .commit();

            }
        });
        return view;

    }



    public void getTask(View view) {
        Bundle bundle = this.getArguments();
        task = new Gson().fromJson(bundle.getString("task"), Task.class);
        Log.d("getTask", "getTask: " + task);
        taskName = (TextView) view.findViewById(R.id.taskName);
        taskName.setText(task.name);

        levelCount = (TextView) view.findViewById(R.id.levelCount);
        levelCount.setText(String.valueOf(task.levelCount));

        descriptionTask = (TextView) view.findViewById(R.id.desriptionTask);
        descriptionTask.setText(task.description);

        location = (TextView) view.findViewById(R.id.location);
        location.setText(task.location);






    }
}