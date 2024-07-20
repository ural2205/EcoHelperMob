package com.example.frontend_app;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @POST("register")
    Call<ResponseBody> register(
            @Query("username") String username,
            @Query("name") String name,
            @Query("surname") String surname,
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("authorization")
    Call<ResponseBody> authorization(
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("get_alltasks")
    Call<List<Task>> getAllTasks();

    @POST("response_task")
    Call<ResponseBody> responseTask(
            @Query("offer") String offer,
            @Query("taskId") int taskId,
            @Query("userId") int userId
    );

    @GET("get_user_profile")
    Call<ModelUserProfile> getUserProfile(
            @Query("user_id") int userId
    );


    @GET("get_allteams")
    Call<List<ModelTeam>> getTeams();

    @POST("invite_team")
    Call<ResponseBody> inviteTeam(
            @Query("offer") String offer,
            @Query("teamId") int teamId,
            @Query("userId") int userId
    );

    @GET("get_user_level")
    Call<ResponseBody> getUserLevel(
            @Query("user_id") int userId
    );

    @POST("create_team")
    Call<ResponseBody> createTeam(
                @Query("name") String name,
                @Query("description") String description,
                @Query("userId") int userId

    );

    @GET("check_status")
    Call<Task> checkStatus(
            @Query("taskId") int taskId
    );

    @POST("change_status")
    Call<ResponseBody> change_status(
            @Query("id_task") int taskId,
            @Query("status") int status
    );

    @POST("check_ready_task")
    Call<ResponseBody> check_ready_task(
            @Query("taskId") int taskId
    );

    @POST("get_xp")
    Call<ResponseBody> get_xp(
            @Query("userId") int userId,
            @Query("taskId") int taskId
    );
}

