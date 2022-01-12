package com.example.countryinformation.RoomArchitecture;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "COUNTRY_INFORMATION")
public class CountryEntity {

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Capital")
    private String capital;

    @ColumnInfo(name = "flag")
    private String flag;

    @ColumnInfo(name = "region")
    private String region;

    @ColumnInfo(name = "subRegion")
    private String subRegion;

    @ColumnInfo(name = "population")
    private String population;

    @ColumnInfo(name = "border")
    private String border;

    @ColumnInfo(name = "language")
    private String language;

    public CountryEntity(@NonNull String name, String capital, String flag, String region, String subRegion, String population, String border, String language) {
        this.name = name;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subRegion = subRegion;
        this.population = population;
        this.border = border;
        this.language = language;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
