package ir.fallahpoor.vicinity.data.repository;

import java.util.List;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.entity.VenuesEntity;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.domain.model.Venue;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class VenuesRepositoryImpl implements VenuesRepository {

    private VenuesEntityDataMapper venuesEntityDataMapper;
    private VenuesWebService venuesWebService;

    public VenuesRepositoryImpl(WebServiceFactory webServiceFactory, VenuesEntityDataMapper venuesEntityDataMapper) {
        this.venuesWebService = webServiceFactory.createService(VenuesWebService.class);
        this.venuesEntityDataMapper = venuesEntityDataMapper;
    }

    @Override
    public Single<List<Venue>> getVenuesAround(double latitude, double longitude) {
        String latLng = latitude + "," + longitude;
        return venuesWebService.getPlacesNear(latLng, "US3XZKWYN5FQMT1X0HNZ5PKM2K3VYHL02ISBT4DGFVPYDU5N",
                "PCEJJJNH3CR0VS0UVYEKCWI4N31KAQMX1V024BSVM34VQNXZ", "20180901")
                .map(venuesEntityDataMapper::transform);
    }

    private interface VenuesWebService {
        @GET("search")
        Single<VenuesEntity> getPlacesNear(@Query("ll") String latLng, @Query("client_id") String clientId,
                                           @Query("client_secret") String clientSecret, @Query("v") String version);
    }

}
