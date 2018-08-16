package ir.fallahpoor.vicinity.data.repository;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;

@android.arch.persistence.room.Database(entities = {VenueEntity.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract VenuesDao venuesDao();

    public static Database getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

}
