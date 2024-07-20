package com.example.frontend_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.function.Consumer;

public class TaskAdapter extends ArrayAdapter<Task> {
    private final Consumer<Task> callback;
    public TaskAdapter(Context context, Task[] arr, Consumer<Task> callback) {
        super(context, R.layout.task_item, arr);
        this.callback = callback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.textView)).setText(task.name);
        ((TextView) convertView.findViewById(R.id.textView2)).setText("Опыт" + String.valueOf(task.levelCount));
        ((TextView) convertView.findViewById(R.id.textView4)).setText(String.valueOf(task.location));

        convertView.setOnClickListener(view -> {
            Log.d("TaskAdapterOnClick", "Task: " + task.name);
            this.callback.accept(task);
        });


        return convertView;
    }
}
