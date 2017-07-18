package ru.lodmisis.mgsu.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.lodmisis.mgsu.viewmodels.NewsModel;
import ru.lodmisis.mgsu.viewmodels.ProjectModel;
import ru.lodmisis.mgsu.viewmodels.User;

/**
 * Created by romanismagilov on 06.06.17.
 */

public interface Endpoints {

    @POST("/user/login")
    Observable<User> login(
            @Body AuthBody body
    );

    @GET("/projects")
    Observable<List<ProjectModel>> getProjects();

    @GET("/posts")
    Observable<List<NewsModel>> getPosts();

    @GET("/user/current")
    Observable<User> getCurrentUser();

}