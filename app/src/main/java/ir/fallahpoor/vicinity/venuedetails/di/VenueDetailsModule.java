package ir.fallahpoor.vicinity.venuedetails.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ir.fallahpoor.vicinity.UiThread;
import ir.fallahpoor.vicinity.common.ExceptionHandler;
import ir.fallahpoor.vicinity.data.executor.JobExecutor;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.Database;
import ir.fallahpoor.vicinity.data.repository.VenuesRepositoryImpl;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;
import ir.fallahpoor.vicinity.data.repository.datasource.VenuesDataSourceFactory;
import ir.fallahpoor.vicinity.domain.executor.PostExecutionThread;
import ir.fallahpoor.vicinity.domain.executor.ThreadExecutor;
import ir.fallahpoor.vicinity.domain.interactors.GetVenueDetailsUseCase;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;
import ir.fallahpoor.vicinity.venuedetails.model.VenueDetailsDataMapper;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenter;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenterImpl;

@Module
public class VenueDetailsModule {

    private Context context;

    public VenueDetailsModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    VenueDetailsPresenter provideVenueDetailsPresenter(GetVenueDetailsUseCase getVenuesUseCase,
                                                       VenueDetailsDataMapper venueDetailsDataMapper,
                                                       ExceptionHandler exceptionHandler) {
        return new VenueDetailsPresenterImpl(getVenuesUseCase, venueDetailsDataMapper, exceptionHandler);
    }

    @Provides
    VenuesRepository provideVenuesRepository(VenuesDataSourceFactory venuesDataSourceFactory, VenuesEntityDataMapper venuesEntityDataMapper) {
        return new VenuesRepositoryImpl(venuesDataSourceFactory, venuesEntityDataMapper);
    }

    @Provides
    ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }

    @Provides
    PostExecutionThread providePostExecutionThread() {
        return new UiThread();
    }

    @Provides
    VenuesDao provideVenuesDao() {
        return Database.getDatabase(context).venuesDao();
    }

}
