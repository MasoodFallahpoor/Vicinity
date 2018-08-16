package ir.fallahpoor.vicinity.data.repository.cache;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;

public class VenuesCache {

    private VenuesDao venuesDao;

    public VenuesCache(VenuesDao venuesDao) {
        this.venuesDao = venuesDao;
    }

    public boolean venueExists(String id) {
        return (venuesDao.venueExists(id) > 0);
    }

    public void saveVenue(VenueEntity venueEntity) {
        venuesDao.saveVenue(venueEntity);
    }

    public Single<VenueEntity> getVenue(String id) {
        return venuesDao.getVenue(id);
    }

}
