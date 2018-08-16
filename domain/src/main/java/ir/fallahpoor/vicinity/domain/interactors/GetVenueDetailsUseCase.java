package ir.fallahpoor.vicinity.domain.interactors;

import io.reactivex.Single;
import ir.fallahpoor.vicinity.domain.executor.PostExecutionThread;
import ir.fallahpoor.vicinity.domain.executor.ThreadExecutor;
import ir.fallahpoor.vicinity.domain.interactors.type.UseCase;
import ir.fallahpoor.vicinity.domain.model.Venue;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;

public class GetVenueDetailsUseCase extends UseCase<Venue, GetVenueDetailsUseCase.Params> {

    private VenuesRepository venuesRepository;

    public GetVenueDetailsUseCase(VenuesRepository venuesRepository,
                                  ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.venuesRepository = venuesRepository;
    }

    @Override
    protected Single<Venue> buildUseCaseObservable(Params params) {
        return venuesRepository.getVenueDetails(params.id);
    }

    public static final class Params {

        private final String id;

        private Params(String id) {
            this.id = id;
        }

        public static Params forVenue(String id) {
            return new Params(id);
        }

    }

}
