package ir.fallahpoor.vicinity.venuedetails;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import ir.fallahpoor.vicinity.R;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenter;
import ir.fallahpoor.vicinity.venuedetails.presenter.VenueDetailsPresenterImpl;
import ir.fallahpoor.vicinity.venuedetails.view.VenueDetailsView;

public class VenueDetailsActivity extends MvpActivity<VenueDetailsView, VenueDetailsPresenter> implements VenueDetailsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
    }

    @NonNull
    @Override
    public VenueDetailsPresenter createPresenter() {
        return new VenueDetailsPresenterImpl();
    }

}
