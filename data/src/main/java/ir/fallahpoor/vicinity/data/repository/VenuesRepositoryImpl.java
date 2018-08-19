package ir.fallahpoor.vicinity.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.datasource.VenuesDataSourceFactory;
import ir.fallahpoor.vicinity.domain.model.Venue;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;

public class VenuesRepositoryImpl implements VenuesRepository {

    private VenuesDataSourceFactory venuesDataSourceFactory;
    private VenuesEntityDataMapper venuesEntityDataMapper;

    public VenuesRepositoryImpl(VenuesDataSourceFactory venuesDataSourceFactory, VenuesEntityDataMapper venuesEntityDataMapper) {
        this.venuesDataSourceFactory = venuesDataSourceFactory;
        this.venuesEntityDataMapper = venuesEntityDataMapper;
    }

    @Override
    public Single<List<Venue>> getVenuesAround(double latitude, double longitude) {
        return venuesDataSourceFactory.create(latitude, longitude)
                .getVenues(latitude, longitude)
                .map(venuesEntityDataMapper::transform);
    }

    @Override
    public Single<Venue> getVenueDetails(String venueId) {
        return venuesDataSourceFactory.create(venueId)
                .getVenue(venueId)
                .map(venuesEntityDataMapper::transformVenue);
    }

}
