package com.example.notesapp.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;
    public abstract NotesDao notesDao();
    private static String DATABASE_NAME ="notesDB";     /* Found in  com.example.ProjectName/data/data/database/databaseName  */

    public static synchronized NotesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NotesDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        PopulateDbAsyncTask(NotesDatabase instance) { NotesDao dao = instance.notesDao(); }

        @Override
        protected Void doInBackground(Void... voids) {  return null; }
    }

}
