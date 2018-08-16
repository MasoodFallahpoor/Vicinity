package ir.fallahpoor.vicinity.domain.repository;

import java.util.List;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.domain.model.Venue;

public interface VenuesRepository {
    Single<List<Venue>> getVenuesAround(final double latitude, final double longitude);

    Single<Venue> getVenueDetails(String id);
}
