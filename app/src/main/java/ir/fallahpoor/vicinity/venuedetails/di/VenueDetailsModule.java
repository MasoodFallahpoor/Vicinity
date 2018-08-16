package ir.fallahpoor.vicinity.venuedetails.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ir.fallahpoor.vicinity.UiThread;
import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.executor.JobExecutor;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.Database;
import ir.fallahpoor.vicinity.data.repository.VenuesRepositoryImpl;
import ir.fallahpoor.vicinity.data.repository.cache.VenuesCache;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;
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
    VenueDetailsPresenter provideVenueDetailsPresenter(GetVenueDetailsUseCase getVenuesUseCase, VenueDetailsDataMapper venueDetailsDataMapper) {
        return new VenueDetailsPresenterImpl(getVenuesUseCase, venueDetailsDataMapper);
    }

    @Provides
    VenuesRepository provideVenuesRepository(VenuesCache venuesCache, WebServiceFactory webServiceFactory,
                                             VenuesEntityDataMapper venuesEntityDataMapper) {
        return new VenuesRepositoryImpl(venuesCache, webServiceFactory, venuesEntityDataMapper);
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
