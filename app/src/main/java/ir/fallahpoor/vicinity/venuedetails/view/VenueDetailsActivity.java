package ir.fallahpoor.vicinity.venuedetails.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import javax.inject.Inject;

import ir.fallahpoor.vicinity.R;
import ir.fallahpoor.vicinity.app.App;
import ir.fallahpoor.vicinity.databinding.ActivityVenueDetailsBinding;
import ir.fallahpoor.vicinity.venuedetails.di.DaggerVenueDetailsComponent;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenter;
import ir.fallahpoor.vicinity.venues.model.VenueViewModel;

public class VenueDetailsActivity extends MvpActivity<VenueDetailsView, VenueDetailsPresenter> implements VenueDetailsView {

    public static final String KEY_VENUE_ID = "venue_id";

    @Inject
    VenueDetailsPresenter venueDetailsPresenter;
    private ActivityVenueDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        injectDependencies();

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_venue_details);

        setupActionBar();

        getPresenter().getVenueDetails(getVenueId());

    }

    private void injectDependencies() {
        DaggerVenueDetailsComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .build()
                .inject(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private String getVenueId() {
        Bundle bundle = getIntent().getExtras();
        return ((bundle == null) ? "" : bundle.getString(KEY_VENUE_ID));
    }

    @NonNull
    @Override
    public VenueDetailsPresenter createPresenter() {
        return venueDetailsPresenter;
    }

    @Override
    public void showLoading() {
        binding.tryAgain.tryAgainLayout.setVisibility(View.GONE);
        binding.loading.loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.loading.loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        binding.tryAgain.errorMessageTextView.setText(errorMessage);
        binding.tryAgain.tryAgainButton.setOnClickListener(view -> getPresenter().getVenueDetails(getVenueId()));
        binding.tryAgain.tryAgainLayout.setVisibility(View.VISIBLE);
        binding.contentLayout.setVisibility(View.GONE);
    }

    @Override
    public void showVenue(VenueViewModel venue) {

        setTitle(venue.getName());

        binding.setVenueViewModel(venue);

        binding.contentLayout.setVisibility(View.VISIBLE);
        binding.tryAgain.tryAgainLayout.setVisibility(View.GONE);

    }

}
