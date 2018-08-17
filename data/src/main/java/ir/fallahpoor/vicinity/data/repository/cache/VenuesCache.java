package ir.fallahpoor.vicinity.data.repository.cache;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.data.entity.Venues;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;

public class VenuesCache {

    private VenuesDao venuesDao;

    @Inject
    public VenuesCache(VenuesDao venuesDao) {
        this.venuesDao = venuesDao;
    }

    public boolean venueExists(String id) {
        return (venuesDao.venueExists(id) > 0);
    }

    public void saveVenue(VenueEntity venueEntity) {
        venuesDao.saveVenue(venueEntity);
    }

    public void replaceVenues(double latitude, double longitude, List<VenueEntity> venues) {
        Venues v = new Venues(latitude, longitude, venues);
        venuesDao.deleteVenues();
        venuesDao.saveVenues(v);
    }

    public Single<VenueEntity> getVenue(String id) {
        return venuesDao.getVenue(id);
    }

    public Single<List<VenueEntity>> getVenues() {
        return venuesDao.getVenuesAsSingle().map(Venues::getVenues);
    }

    public boolean venuesExistFor(double latitude, double longitude) {
        return !isEmpty() && areVenuesRelevantTo(latitude, longitude);
    }

    private boolean isEmpty() {
        return (venuesDao.getNumberOfVenues() == 0);
    }

    private boolean areVenuesRelevantTo(double latitude, double longitude) {
        Venues venues = venuesDao.getVenues();
        return distanceInMeters(latitude, longitude, venues.getLatitude(), venues.getLongitude()) <= 100;
    }

    private double distanceInMeters(double latitude1, double longitude1, double latitude2, double longitude2) {

        final int radiusOfEarth = 6371;

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radiusOfEarth * c * 1000;

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);

    }

}
