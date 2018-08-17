package ir.fallahpoor.vicinity.data.repository;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ir.fallahpoor.vicinity.data.entity.VenueEntity;

public class DataConverter {

    @TypeConverter
    public String fromVenueEntityList(List<VenueEntity> venueEntities) {

        if (venueEntities == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<VenueEntity>>() {
        }.getType();
        return gson.toJson(venueEntities, type);

    }

    @TypeConverter
    public List<VenueEntity> toVenueEntityList(String venueEntitiesString) {

        if (venueEntitiesString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<VenueEntity>>() {
        }.getType();

        return gson.fromJson(venueEntitiesString, type);

    }

}
