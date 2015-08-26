package com.juanpabloprado.android.notesandroid;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.juanpabloprado.android.notesandroid.api.Notes;
import com.juanpabloprado.android.notesandroid.model.Note;
import com.juanpabloprado.android.notesandroid.ui.EditNoteActivity;
import com.juanpabloprado.android.notesandroid.ui.MainActivity;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
/**
 * Created by Juan on 8/7/2015.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> implements Callback<List<Note>>{

    public static final String NOTE_EDIT = "NOTE_EDIT";
    private MainActivity mActivity;
    private LayoutInflater mInflater;
    private List<Note> mNotes;

    public NoteAdapter(MainActivity activity) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NoteHolder(mInflater.inflate(R.layout.layout_note, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(NoteHolder noteHolder, int i) {
        final Note note = mNotes.get(i);
        noteHolder.titleView.setText(note.title);
        noteHolder.contentView.setText(note.content);

        NotesAndroidSettings settings = new NotesAndroidSettings(mActivity.getApplicationContext());
        final String tokenPreference = settings.getAccessTokenPreference();
        final String userIdPreference = settings.getUsernamePreference();

        noteHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity.getApplicationContext(), EditNoteActivity.class);
                intent.putExtra(NOTE_EDIT, note);
                mActivity.startActivity(intent);
            }
        });

        noteHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes.deleteNote(tokenPreference, userIdPreference + "", note.id + "", new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(mActivity.getApplicationContext(), "Deleted " + note.title, Toast.LENGTH_LONG).show();
                        mNotes.remove(note);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void success(List<Note> notes, Response response) {
        mNotes = notes;
        notifyDataSetChanged();
        mActivity.showList();
    }

    @Override
    public void failure(RetrofitError error) {
        mActivity.showError();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView contentView;
        public Button editButton;
        public Button deleteButton;

        public NoteHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.note_title);
            contentView = (TextView) itemView.findViewById(R.id.note_content);
            editButton = (Button) itemView.findViewById(R.id.editButton);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);
        }
    }
}
