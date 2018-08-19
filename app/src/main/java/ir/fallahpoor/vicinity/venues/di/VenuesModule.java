package ir.fallahpoor.vicinity.venues.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ir.fallahpoor.vicinity.common.ExceptionHandler;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.Database;
import ir.fallahpoor.vicinity.data.repository.VenuesRepositoryImpl;
import ir.fallahpoor.vicinity.data.repository.dao.VenuesDao;
import ir.fallahpoor.vicinity.data.repository.datasource.VenuesDataSourceFactory;
import ir.fallahpoor.vicinity.domain.interactors.GetVenuesUseCase;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;
import ir.fallahpoor.vicinity.venues.model.VenuesDataMapper;
import ir.fallahpoor.vicinity.venues.presenter.VenuesPresenter;
import ir.fallahpoor.vicinity.venues.presenter.VenuesPresenterImpl;

@Module
public class VenuesModule {

    @Provides
    VenuesPresenter provideVenuesPresenter(GetVenuesUseCase getVenuesUseCase, VenuesDataMapper venuesDataMapper,
                                           ExceptionHandler exceptionHandler) {
        return new VenuesPresenterImpl(getVenuesUseCase, venuesDataMapper, exceptionHandler);
    }

    @Provides
    VenuesRepository provideVenuesRepository(VenuesDataSourceFactory venuesDataSourceFactory,
                                             VenuesEntityDataMapper venuesEntityDataMapper) {
        return new VenuesRepositoryImpl(venuesDataSourceFactory, venuesEntityDataMapper);
    }

    @Provides
    VenuesDao provideVenuesDao(Context context) {
        return Database.getDatabase(context).venuesDao();
    }

}
