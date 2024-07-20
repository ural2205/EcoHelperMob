package com.example.frontend_app;

import com.google.gson.annotations.SerializedName;

public class ModelTeam {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("participants")
    String participants;

    @SerializedName("description")
    String description;
}
