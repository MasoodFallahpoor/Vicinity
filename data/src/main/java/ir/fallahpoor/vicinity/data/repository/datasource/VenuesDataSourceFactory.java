package ir.fallahpoor.vicinity.data.repository.datasource;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.data.repository.cache.VenuesCache;

public class VenuesDataSourceFactory {

    private LocalVenuesDataSource localVenuesDataStore;
    private CloudVenuesDataSource cloudVenuesDataStore;
    private VenuesCache venuesCache;

    @Inject
    VenuesDataSourceFactory(VenuesCache venuesCache, LocalVenuesDataSource localVenuesDataStore, CloudVenuesDataSource cloudVenuesDataStore) {
        this.venuesCache = venuesCache;
        this.localVenuesDataStore = localVenuesDataStore;
        this.cloudVenuesDataStore = cloudVenuesDataStore;
    }

    public VenuesDataSource create(final double latitude, final double longitude) {
        return (venuesCache.venuesExistFor(latitude, longitude) ? localVenuesDataStore : cloudVenuesDataStore);
    }

    public VenuesDataSource create(String venueId) {
        return (venuesCache.venueExists(venueId) ? localVenuesDataStore : cloudVenuesDataStore);
    }

}
