package com.juanpabloprado.android.notesandroid.api;

import com.juanpabloprado.android.notesandroid.model.AccessToken;
import com.juanpabloprado.android.notesandroid.model.Credentials;
import com.juanpabloprado.android.notesandroid.model.Note;
import com.juanpabloprado.android.notesandroid.model.NoteDTO;
import com.juanpabloprado.android.notesandroid.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Juan on 6/1/2015.
 */
public interface Api {

    @POST("/users")
    void createUser(@Body User user, Callback<User> callback);

    @POST("/tokens")
    void loginUser(@Body Credentials credentials, Callback<AccessToken> callback);

    @GET("/users/{username}/notes")
    void listNotes(@Path("username") String username, Callback<List<Note>> callback);

    @POST("/users/{username}/notes")
    void createNote(@Path("username") String username, @Body NoteDTO note, Callback<Note> callback);

    @POST("/users/{username}/notes/{noteId}")
    void editNote(@Path("username") String username, @Path("noteId") String noteId, @Body Note note, Callback<Note> callback);

    @DELETE("/users/{username}/notes/{noteId}")
    void deleteNote(@Path("username") String username, @Path("noteId") String noteId, Callback<Response> callback);

}
