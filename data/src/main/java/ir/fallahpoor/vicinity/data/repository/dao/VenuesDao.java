package ir.fallahpoor.vicinity.data.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;

@Dao
public interface VenuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveVenue(VenueEntity venueEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveVenues(List<VenueEntity> venues);

    @Query("SELECT * FROM venues WHERE id = :venueId")
    Single<VenueEntity> getVenue(String venueId);

    @Query("SELECT * FROM venues")
    Single<List<VenueEntity>> getVenues();

    @Query("SELECT COUNT(*) FROM venues WHERE id = :venueId")
    int venueExists(String venueId);

    @Query("DELETE FROM venues")
    void deleteVenues();

    @Query("SELECT COUNT(*) FROM venues")
    int getNumberOfVenues();

}
