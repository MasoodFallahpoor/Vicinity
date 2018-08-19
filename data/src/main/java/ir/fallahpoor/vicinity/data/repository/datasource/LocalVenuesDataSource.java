package ir.fallahpoor.vicinity.data.repository.datasource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.data.repository.cache.VenuesCache;

public class LocalVenuesDataSource implements VenuesDataSource {

    private VenuesCache venuesCache;

    @Inject
    public LocalVenuesDataSource(VenuesCache venuesCache) {
        this.venuesCache = venuesCache;
    }


    @Override
    public Single<List<VenueEntity>> getVenues(double latitude, double longitude) {
        return venuesCache.getVenues();
    }

    @Override
    public Single<VenueEntity> getVenue(final String venueId) {
        return venuesCache.getVenue(venueId);
    }

}
