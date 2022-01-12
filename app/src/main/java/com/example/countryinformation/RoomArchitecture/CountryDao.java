package com.example.countryinformation.RoomArchitecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CountryEntity countryEntity);

    @Transaction
    @Query("SELECT * FROM COUNTRY_INFORMATION")
    LiveData<List<CountryEntity>> getAllInfo();

    @Query("DELETE FROM COUNTRY_INFORMATION")
    void deleteAll();
}
