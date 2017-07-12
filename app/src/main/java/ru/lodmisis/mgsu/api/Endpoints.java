package ru.lodmisis.mgsu.api;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.lodmisis.mgsu.bodies.AuthBody;
import ru.lodmisis.mgsu.models.Project;
import ru.lodmisis.mgsu.models.User;

/**
 * Created by romanismagilov on 06.06.17.
 */

public interface Endpoints {

    @POST("/user/login")
    @Headers({"Content-Type: application/json"})
    Observable<User> login(
            @Body AuthBody body
    );

    @GET("/projects")
    @Headers({"Content-Type: application/json"})
    Observable<List<Project>> getProjects();

    @GET("/user/current")
    @Headers({"Content-Type: application/json"})
    Observable<User> getCurrentUser();

}