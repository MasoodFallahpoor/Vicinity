package ir.fallahpoor.vicinity.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import ir.fallahpoor.vicinity.data.repository.DataConverter;

@Entity(tableName = "venues", primaryKeys = {"latitude", "longitude"})
public class Venues {

    private double latitude;
    private double longitude;
    @TypeConverters(DataConverter.class)
    private List<VenueEntity> venues;

    public Venues() {
    }

    public Venues(double latitude, double longitude, List<VenueEntity> venues) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.venues = venues;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<VenueEntity> getVenues() {
        return venues;
    }

    public void setVenues(List<VenueEntity> venues) {
        this.venues = venues;
    }

}
