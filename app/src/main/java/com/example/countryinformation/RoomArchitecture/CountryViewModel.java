package com.example.countryinformation.RoomArchitecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    private final CountryRepository repository;
    private final LiveData<List<CountryEntity>> allInfo;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        repository = new CountryRepository(application);
        allInfo = repository.getAllCountryInfo();
    }
    //Observing all live room database data method.
    public LiveData<List<CountryEntity>> getAllCountryInfo(){
        return allInfo;
    }
    //Fetch Json data method
    public void fetchJSONData(){
        repository.fetchJSONData();
    }
    //Delete all data from database method.
    public void deleteAll(){
        repository.deleteAll();
    }
}
