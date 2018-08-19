package ir.fallahpoor.vicinity.data.repository.datasource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.entity.VenueDetailsEntity;
import ir.fallahpoor.vicinity.data.entity.VenueEntity;
import ir.fallahpoor.vicinity.data.entity.VenuesEntity;
import ir.fallahpoor.vicinity.data.repository.cache.VenuesCache;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CloudVenuesDataSource implements VenuesDataSource {

    private static final String CLIENT_ID = "IJZDVIXBO10YGCTXHD0UV0QO2QUCW0L34J2DGUFQBGI2FV0X";
    private static final String CLIENT_SECRET = "RLRAMRXMPEI0K22XTWTNP3ZFJ2REXTVIOV45UCLLCRFBZ12A";
    private static final String VERSION = "20180901";

    private VenuesWebService venuesWebService;
    private VenuesCache venuesCache;

    @Inject
    public CloudVenuesDataSource(WebServiceFactory webServiceFactory, VenuesCache venuesCache) {
        this.venuesWebService = webServiceFactory.createService(VenuesWebService.class);
        this.venuesCache = venuesCache;
    }

    @Override
    public Single<List<VenueEntity>> getVenues(double latitude, double longitude) {

        String latLng = latitude + "," + longitude;

        return venuesWebService.getVenuesNear(latLng, CLIENT_ID, CLIENT_SECRET, VERSION)
                .doOnSuccess(venuesEntity -> venuesCache.replaceVenues(latitude, longitude, venuesEntity.getResponse().getVenues()))
                .map(venuesEntity -> venuesEntity.getResponse().getVenues());

    }

    @Override
    public Single<VenueEntity> getVenue(String venueId) {
        return venuesWebService.getVenueDetails(venueId, CLIENT_ID, CLIENT_SECRET, VERSION)
                .doOnSuccess(venueDetailsEntity -> venuesCache.saveVenue(venueDetailsEntity.getResponse().getVenue()))
                .map(venueDetailsEntity -> venueDetailsEntity.getResponse().getVenue());
    }

    private interface VenuesWebService {
        @GET("search")
        Single<VenuesEntity> getVenuesNear(@Query("ll") String latLng, @Query("client_id") String clientId,
                                           @Query("client_secret") String clientSecret, @Query("v") String version);

        @GET("{venueId}")
        Single<VenueDetailsEntity> getVenueDetails(@Path("venueId") String venueId, @Query("client_id") String clientId,
                                                   @Query("client_secret") String clientSecret, @Query("v") String version);
    }

}
