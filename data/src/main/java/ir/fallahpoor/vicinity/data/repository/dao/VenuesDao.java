package ir.fallahpoor.vicinity.data.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.entity.Venues;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;

@Dao
public interface VenuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveVenue(VenueEntity venueEntity);

    @Query("SELECT * FROM venue WHERE id = :venueId")
    Single<VenueEntity> getVenue(String venueId);

    @Query("SELECT COUNT(*) FROM venue WHERE id = :venueId")
    int venueExists(String venueId);

    @Query("SELECT * FROM venues")
    Single<Venues> getVenuesAsSingle();

    @Query("SELECT * FROM venues")
    Venues getVenues();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveVenues(Venues venues);

    @Query("DELETE FROM venues")
    void deleteVenues();

    @Query("SELECT COUNT(*) FROM venues")
    int getNumberOfVenues();

}

