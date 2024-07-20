package com.example.frontend_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

public class TeamAdapter extends ArrayAdapter<ModelTeam> {
    private final Consumer<ModelTeam> callback;

    public TeamAdapter(Context context, ModelTeam[] arr, Consumer<ModelTeam> callback) {
        super(context, R.layout.team_item, arr);
        this.callback = callback;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ModelTeam modelTeam = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.team_item, null);
        }

        ((TextView) convertView.findViewById(R.id.teamName)).setText(modelTeam.name);
        ((TextView) convertView.findViewById(R.id.teamParticipants)).setText(modelTeam.participants);


        convertView.setOnClickListener(view ->{
            this.callback.accept(modelTeam);
        });
        return convertView;
    }
}
