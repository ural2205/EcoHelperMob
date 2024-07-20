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

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInviteTeam extends Fragment {

    SharedPreferences mSettings;

    EditText editTextOffer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_team, container, false);
        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        int userId = 0;
        int userIdAuth = 0;

        if(mSettings.contains(APP_PREFERENCES_USERID)) {
            userId = mSettings.getInt(APP_PREFERENCES_USERID, userId);
            userIdAuth = mSettings.getInt(APP_PREFERENCES_USERID,userIdAuth);
        }
        Button sendOfferInvite = view.findViewById(R.id.sendOfferInviteToTeam);
        int finalUserId = userId;
        int finalUserIdAuth = userIdAuth;
        sendOfferInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InviteToTeam(finalUserId, finalUserIdAuth);
            }
        });
        editTextOffer = (EditText) view.findViewById(R.id.offerInviteTeam);


        Button back = view.findViewById(R.id.backToTeam);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Team.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }



    public void InviteToTeam(int userId, int userIdAuth){
        if (userIdAuth != 0) {
            userId = userIdAuth;
        }


        String offer = editTextOffer.getText().toString();

        Bundle bundle = this.getArguments();
        ModelTeam modelTeam = new Gson().fromJson(bundle.getString("team"), ModelTeam.class);

        retrofit2.Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .inviteTeam(offer, modelTeam.id, userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = response.body().toString();
                Toast.makeText(getActivity(), "Отправлено", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}