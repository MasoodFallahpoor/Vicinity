package ir.fallahpoor.vicinity.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.entity.VenueDetailsEntity;
import ir.fallahpoor.vicinity.data.entity.VenuesEntity;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.domain.model.Venue;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class VenuesRepositoryImpl implements VenuesRepository {

    private static final String CLIENT_ID = "IJZDVIXBO10YGCTXHD0UV0QO2QUCW0L34J2DGUFQBGI2FV0X";
    private static final String SECRET_ID = "RLRAMRXMPEI0K22XTWTNP3ZFJ2REXTVIOV45UCLLCRFBZ12A";
    private static final String VERSION = "20180901";

    private VenuesEntityDataMapper venuesEntityDataMapper;
    private VenuesWebService venuesWebService;

    public VenuesRepositoryImpl(WebServiceFactory webServiceFactory, VenuesEntityDataMapper venuesEntityDataMapper) {
        this.venuesWebService = webServiceFactory.createService(VenuesWebService.class);
        this.venuesEntityDataMapper = venuesEntityDataMapper;
    }

    @Override
    public Single<List<Venue>> getVenuesAround(double latitude, double longitude) {
        String latLng = latitude + "," + longitude;
        return venuesWebService.getPlacesNear(latLng, CLIENT_ID, SECRET_ID, VERSION)
                .map(venuesEntityDataMapper::transform);
    }

    @Override
    public Single<Venue> getVenueDetails(String id) {
        return venuesWebService.getVenueDetails(id, CLIENT_ID, SECRET_ID, VERSION)
                .map(venueDetailsEntity -> venuesEntityDataMapper.transformVenue(venueDetailsEntity.getResponse().getVenue()));
    }

    private interface VenuesWebService {
        @GET("search")
        Single<VenuesEntity> getPlacesNear(@Query("ll") String latLng, @Query("client_id") String clientId,
                                           @Query("client_secret") String clientSecret, @Query("v") String version);

        @GET("{venueId}")
        Single<VenueDetailsEntity> getVenueDetails(@Path("venueId") String venueId, @Query("client_id") String clientId,
                                                   @Query("client_secret") String clientSecret, @Query("v") String version);
    }

}
