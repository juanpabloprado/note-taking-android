package com.juanpabloprado.android.notesandroid.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.juanpabloprado.android.notesandroid.NoteAdapter;
import com.juanpabloprado.android.notesandroid.NotesAndroidSettings;
import com.juanpabloprado.android.notesandroid.R;
import com.juanpabloprado.android.notesandroid.api.Notes;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private View mProgressBar;
    private TextView mErrorView;
    private FloatingActionButton mFloatingActionButton;
    private NoteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesAndroidSettings settings = new NotesAndroidSettings(getApplicationContext());
        String preference = settings.getAccessTokenPreference();
        if (preference == null) {
            navigateToLogin();
        } else {
            Log.i(TAG, preference);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBar);
        mErrorView = (TextView) findViewById(R.id.errorView);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.addButton);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                startActivity(intent);
            }
        });
        // setup recyclerView
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new NoteAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        showLoading();

        String token = settings.getAccessTokenPreference();
        String userId = settings.getUsernamePreference() + "";
        Notes.listNotes(token, userId, mAdapter);

    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            NotesAndroidSettings settings = new NotesAndroidSettings(getApplicationContext());
            settings.setUsernamePreference(null);
            settings.setAccessTokenPreference(null);
            navigateToLogin();
        }

        return super.onOptionsItemSelected(item);
    }


    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    public void showList() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }
}
