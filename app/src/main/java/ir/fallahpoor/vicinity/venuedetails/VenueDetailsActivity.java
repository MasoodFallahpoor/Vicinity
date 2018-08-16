package ir.fallahpoor.vicinity.venuedetails;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import ir.fallahpoor.vicinity.R;
import ir.fallahpoor.vicinity.UiThread;
import ir.fallahpoor.vicinity.data.WebServiceFactory;
import ir.fallahpoor.vicinity.data.executor.JobExecutor;
import ir.fallahpoor.vicinity.data.mapper.VenuesEntityDataMapper;
import ir.fallahpoor.vicinity.data.repository.VenuesRepositoryImpl;
import ir.fallahpoor.vicinity.domain.interactors.GetVenueDetailsUseCase;
import ir.fallahpoor.vicinity.domain.repository.VenuesRepository;
import ir.fallahpoor.vicinity.venuedetails.model.VenueDetailsDataMapper;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenter;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenterImpl;
import ir.fallahpoor.vicinity.venuedetails.view.VenueDetailsView;
import ir.fallahpoor.vicinity.venues.model.VenueViewModel;

public class VenueDetailsActivity extends MvpActivity<VenueDetailsView, VenueDetailsPresenter> implements VenueDetailsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
    }

    @NonNull
    @Override
    public VenueDetailsPresenter createPresenter() {
        VenuesRepository venuesRepository = new VenuesRepositoryImpl(new WebServiceFactory(), new VenuesEntityDataMapper());
        GetVenueDetailsUseCase getPlaceDetailsUseCase = new GetVenueDetailsUseCase(venuesRepository, new JobExecutor(), new UiThread());
        return new VenueDetailsPresenterImpl(getPlaceDetailsUseCase, new VenueDetailsDataMapper());
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
    public void showPlace(VenueViewModel venue) {
    }

}
