package com.example.frontend_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FragmentTeamDescription extends Fragment {

    private ModelTeam modelTeam;

    TextView nameTeam, participants, descriptionTeam;

    Button backTeam, inviteTeam;
    public FragmentTeamDescription() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_description, container, false);
        getTeam(view);
        backTeam = view.findViewById(R.id.backTeam);
        backTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Team.class);
                getActivity().startActivity(intent);
            }
        });

        inviteTeam = view.findViewById(R.id.inviteTeam);
        inviteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentInviteTeam f1 = new FragmentInviteTeam();
                Bundle bundle = new Bundle();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                String teamJson = gson.toJson(modelTeam);
                bundle.putString("team", teamJson);
                f1.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.conteinerTeam, f1)
                        .commit();
            }
        });
        return view;
    }

    public void getTeam(View view){
        Bundle bundle = this.getArguments();
        modelTeam = new Gson().fromJson(bundle.getString("team"), ModelTeam.class);

        nameTeam = (TextView) view.findViewById(R.id.nameTeam);
        nameTeam.setText(modelTeam.name);

        participants = (TextView) view.findViewById(R.id.participants);
        participants.setText(modelTeam.participants);
        Log.d("getTask", "getTask: " + modelTeam);
        descriptionTeam = (TextView) view.findViewById(R.id.descriptionTeam);
        descriptionTeam.setText(modelTeam.description);
    }
}