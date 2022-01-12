package com.example.countryinformation.RoomArchitecture;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {CountryEntity.class}, version = 1, exportSchema = false)
public abstract class CountryDatabase extends RoomDatabase {

    public abstract CountryDao countryDao();
    private static volatile CountryDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static CountryDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (CountryDatabase.class){
                INSTANCE = Room.databaseBuilder(context, CountryDatabase.class, "COUNTRY_DATABASE")
                        .addCallback(sRoomCallback)
                        .build();
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback sRoomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            CountryDao countryDao = INSTANCE.countryDao();
            databaseWriteExecutor.execute(countryDao::deleteAll);
        }
    };
}
