package ir.fallahpoor.vicinity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.executor.JobExecutor;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.VenuesRepositoryImpl;
import ir.fallahpoor.vicinity.domain.interactors.GetVenuesUseCase;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;

public class VenuesActivity extends MvpActivity<VenuesView, VenuesPresenter> implements VenuesView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);
    }

    @NonNull
    @Override
    public VenuesPresenter createPresenter() {
        VenuesRepository venuesRepository = new VenuesRepositoryImpl(new WebServiceFactory(), new VenuesEntityDataMapper());
        GetVenuesUseCase getVenuesUseCase = new GetVenuesUseCase(venuesRepository, new JobExecutor(), new UiThread());
        return new VenuesPresenterImpl(getVenuesUseCase, new VenuesDataMapper());
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String errorMessage) {
    }

    @Override
    public void showVenues(List<VenueViewModel> venues) {
    }

}
