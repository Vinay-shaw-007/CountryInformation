package com.example.countryinformation.RoomArchitecture;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.countryinformation.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class CountryRepository {
    private final CountryDao countryDao;
    private final LiveData<List<CountryEntity>> countryEntityLiveData;
    private final Application application;

    public CountryRepository(Application application) {
        this.application = application;
        CountryDatabase countryDatabase = CountryDatabase.getInstance(application);
        countryDao = countryDatabase.countryDao();
        countryEntityLiveData = countryDao.getAllInfo();
    }

    LiveData<List<CountryEntity>> getAllCountryInfo(){
        return countryEntityLiveData;
    }
    //Insert in database method.
    void insert(CountryEntity entity){
        CountryDatabase.databaseWriteExecutor.execute(() -> countryDao.insert(entity));
    }
    //Delete all data from database method.
    void deleteAll(){
        CountryDatabase.databaseWriteExecutor.execute(countryDao::deleteAll);
    }
    void fetchJSONData(){
        CountryDatabase.databaseWriteExecutor.execute(this::fetchInformation);
    }

    //Fetching data from JSON Api
    void fetchInformation(){
        String url = "https://restcountries.com/v3.1/region/asia";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    Iterator<?> keys;
                    String name;
                    String capital;
                    String region;
                    String subRegion;
                    String population;
                    String flag;
                    String languages;
                    StringBuilder Language= new StringBuilder();
                    StringBuilder Borders= new StringBuilder();

                    region = jsonObject.getString("region");

                    subRegion = jsonObject.getString("subregion");

                    population = jsonObject.getString("population");

                    name = jsonObject.getJSONObject("name").getString("common");

                    capital = jsonObject.getJSONArray("capital").getString(0);

                    flag = jsonObject.getJSONObject("flags").getString("png");

                    JSONArray jsonArray = jsonObject.getJSONArray("borders");
                    for (int j = 0; j < jsonArray.length(); j++){
                        Borders.append(jsonArray.get(j).toString()).append(" ");
                    }

                    languages = jsonObject.getString("languages");
                    JSONObject jsonObject1 = new JSONObject(languages);
                    keys = jsonObject1.keys();
                    while (keys.hasNext()){
                        String nextKey = (String) keys.next();
                        Language.append(jsonObject1.getString(nextKey)).append(" ");
                    }
                    CountryEntity entity = new CountryEntity(name, capital, flag, region, subRegion, population, Borders.toString(), Language.toString());
                    insert(entity);
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(application, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, Throwable::getLocalizedMessage);
        MySingleton.getInstance(application.getApplicationContext()).addToRequestQueue(request);
    }
}
