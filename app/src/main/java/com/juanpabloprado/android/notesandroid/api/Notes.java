package com.juanpabloprado.android.notesandroid.api;


import com.juanpabloprado.android.notesandroid.model.AccessToken;
import com.juanpabloprado.android.notesandroid.model.Credentials;
import com.juanpabloprado.android.notesandroid.model.Note;
import com.juanpabloprado.android.notesandroid.model.NoteDTO;
import com.juanpabloprado.android.notesandroid.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;

/**
 * Created by Juan on 5/28/2015.
 */
public class Notes {

    private static String mAccessToken = null;
    private static final String TOKEN_TYPE = "Bearer";

    private static RequestInterceptor getInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", TOKEN_TYPE + " " + mAccessToken);
            }
        };
    }

    private static Api getApi() {
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("http://10.0.2.2:8080/v1");
        if(mAccessToken  != null) {
            builder.setRequestInterceptor(getInterceptor());
        }
        return builder.build().create(Api.class);
    }

    public static void createUser(User user, Callback<User> callback) {
        getApi().createUser(user, callback);
    }

    public static void loginUser(Credentials credentials, Callback<AccessToken> callback) {
        getApi().loginUser(credentials, callback);
    }

    public static void listNotes(String accessToken, String userId, Callback<List<Note>> callback) {
        mAccessToken = accessToken;
        getApi().listNotes(userId, callback);
    }

    public static void createNote(String accessToken, String userId, NoteDTO note, Callback<Note> callback) {
        mAccessToken = accessToken;
        getApi().createNote(userId, note, callback);
    }

    public static void editNote(String accessToken, String userId, String noteId, Note note, Callback<Note> callback) {
        mAccessToken = accessToken;
        getApi().editNote(userId, noteId, note, callback);
    }

    public static void deleteNote(String accessToken, String userId, String noteId, Callback<Response> callback) {
        mAccessToken = accessToken;
        getApi().deleteNote(userId, noteId, callback);
    }
}
