package ir.fallahpoor.vicinity.domain.interactors;

import java.util.List;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.domain.executor.PostExecutionThread;
import ir.fallahpoor.vicinity.domain.executor.ThreadExecutor;
import ir.fallahpoor.vicinity.domain.interactors.type.UseCase;
import ir.fallahpoor.vicinity.domain.model.Venue;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;

public class GetVenuesUseCase extends UseCase<List<Venue>, GetVenuesUseCase.Params> {

    private VenuesRepository venuesRepository;

    public GetVenuesUseCase(VenuesRepository venuesRepository,
                            ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.venuesRepository = venuesRepository;
    }

    @Override
    protected Single<List<Venue>> buildUseCaseObservable(Params params) {
        return venuesRepository.getVenuesAround(params.latitude, params.longitude);
    }

    public static final class Params {

        private final double latitude;
        private final double longitude;

        private Params(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public static Params forLocation(double latitude, double longitude) {
            return new Params(latitude, longitude);
        }

    }

}
