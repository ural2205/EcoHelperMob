package com.example.frontend_app;

import com.google.gson.annotations.SerializedName;

public class ModelUserProfile {
    @SerializedName("userId")
    String id;

    @SerializedName("username")
    String username;

    @SerializedName("name")
    String name;

    @SerializedName("surname")
    String surname;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("level")
    int level;

    @SerializedName("xp")
    int xp;
}
