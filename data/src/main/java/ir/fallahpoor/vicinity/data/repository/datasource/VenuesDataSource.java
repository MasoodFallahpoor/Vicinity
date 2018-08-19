package ir.fallahpoor.vicinity.data.repository.datasource;

import java.util.List;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;

public interface VenuesDataSource {
    Single<List<VenueEntity>> getVenues(double latitude, double longitude);

    Single<VenueEntity> getVenue(final String venueId);
}
