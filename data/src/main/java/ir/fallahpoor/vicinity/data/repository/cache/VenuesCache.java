package ir.fallahpoor.vicinity.data.repository.cache;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;

public class VenuesCache {

    private VenuesDao venuesDao;

    @Inject
    public VenuesCache(VenuesDao venuesDao) {
        this.venuesDao = venuesDao;
    }

    public boolean isEmpty() {
        return (venuesDao.getNumberOfVenues() == 0);
    }

    public boolean venueExists(String id) {
        return (venuesDao.venueExists(id) > 0);
    }

    public void saveVenue(VenueEntity venueEntity) {
        venuesDao.saveVenue(venueEntity);
    }

    public void replaceVenues(List<VenueEntity> venues) {
        deleteVenues();
        venuesDao.saveVenues(venues);
    }

    public Single<VenueEntity> getVenue(String id) {
        return venuesDao.getVenue(id);
    }

    public Single<List<VenueEntity>> getVenues() {
        return venuesDao.getVenues();
    }

    private void deleteVenues() {
        venuesDao.deleteVenues();
    }

}
