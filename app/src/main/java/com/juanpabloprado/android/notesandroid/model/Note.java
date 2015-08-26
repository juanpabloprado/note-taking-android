package com.juanpabloprado.android.notesandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Juan on 8/7/2015.
 */
public class Note implements Parcelable {
    public int id;
    public String title;
    public String content;

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
    }
}
