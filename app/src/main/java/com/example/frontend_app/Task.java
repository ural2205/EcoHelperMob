package com.example.frontend_app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Task implements Parcelable {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("level_count")
    int levelCount;

    @SerializedName("description")
    String description;

    @SerializedName("location")
    String location;

    @SerializedName("status")
    private int status;


    public Task(int id, String name, int levelCount, String description, String location, int status) {
        this.id = id;
        this.name = name;
        this.levelCount = levelCount;
        this.description = description;
        this.location = location;
        this.status = status;
    }


    public int getLevelCount() {
        return levelCount;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", levelCount=" + levelCount +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status +
                '}';
    }

    public String[] getAll(){
        String[] allInfo = {getName(), getDescription(),"" + getLevelCount(), "" +  getStatus()};
        return allInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeStringArray(getAll());
    }
}
