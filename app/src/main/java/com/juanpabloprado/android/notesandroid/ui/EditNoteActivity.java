package com.juanpabloprado.android.notesandroid.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.juanpabloprado.android.notesandroid.NoteAdapter;
import com.juanpabloprado.android.notesandroid.NotesAndroidSettings;
import com.juanpabloprado.android.notesandroid.R;
import com.juanpabloprado.android.notesandroid.api.Notes;
import com.juanpabloprado.android.notesandroid.model.NoteDTO;
import com.juanpabloprado.android.notesandroid.model.Note;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EditNoteActivity extends AppCompatActivity {

    protected EditText mTitle;
    protected EditText mContent;
    protected Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        mTitle = (EditText) findViewById(R.id.titleField);
        mContent = (EditText) findViewById(R.id.contentField);
        mCreateButton = (Button) findViewById(R.id.createButton);

        Intent intent = getIntent();
        final Note note = intent.getParcelableExtra(NoteAdapter.NOTE_EDIT);

        if (note != null) {
            mTitle.setText(note.title);
            mContent.setText(note.content);
            mCreateButton.setText("Update");
            mCreateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.title = mTitle.getText().toString();
                    note.content = mContent.getText().toString();

                    if (note.title.isEmpty() || note.content.isEmpty()) {
                        alertUserAboutError();
                    } else {
                        setProgressBarIndeterminateVisibility(true);
                        NotesAndroidSettings settings = new NotesAndroidSettings(getApplicationContext());
                        String tokenPreference = settings.getAccessTokenPreference();
                        String usernamePreference = settings.getUsernamePreference();
                        Notes.editNote(tokenPreference, usernamePreference + "", note.id + "", note, new Callback<Note>() {
                            @Override
                            public void success(Note note, Response response) {
                                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
                                builder.setMessage(error.getMessage())
                                        .setTitle(R.string.note_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                }
            });

        } else {

            // create
            mCreateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = mTitle.getText().toString();
                    String content = mContent.getText().toString();

                    if (title.isEmpty() || content.isEmpty()) {
                        alertUserAboutError();
                    } else {
                        setProgressBarIndeterminateVisibility(true);

                        NotesAndroidSettings settings = new NotesAndroidSettings(getApplicationContext());
                        String tokenPreference = settings.getAccessTokenPreference();
                        String usernamePreference = settings.getUsernamePreference();
                        NoteDTO newNote = new NoteDTO(title, content);
                        Notes.createNote(tokenPreference, usernamePreference + "", newNote, new Callback<Note>() {
                            @Override
                            public void success(Note note, Response response) {
                                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
                                builder.setMessage(error.getMessage())
                                        .setTitle(R.string.note_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                }
            });
        }
    }

    private void alertUserAboutError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
        builder.setMessage(R.string.note_error_message)
                .setTitle(R.string.note_error_title)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
